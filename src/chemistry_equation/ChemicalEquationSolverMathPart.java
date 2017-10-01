package chemistry_equation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Александр on 19.09.2017.
 */
public class ChemicalEquationSolverMathPart implements ChemistryPatterns, ChemistryEquationMessages{

    private ChemicalEquationSolverMathPart(){}

    public static String solveChemistryEquation(String text) throws ParsingEquationException, ElementsAndSubstancesException, ArrayIndexOutOfBoundsException{

        int posArrow = text.indexOf(MESSAGE_CULTIVATOR);
        if(posArrow == -1){
            throw new ParsingEquationException(String.format(MESSAGE_error_missed_cultivator, MESSAGE_CULTIVATOR));
        }

        Set<Substance> leftSubstances = new TreeSet<>();
        Set<Substance> rightSubstances = new TreeSet<>();

        String[] leftPart = text.substring(0, posArrow).split("\\+");
        for(String subName : leftPart) {
            cheakAndRefactorSubstance(subName);
            Substance substance = new Substance(subName);
            if(leftSubstances.contains(substance)){
                throw new ParsingEquationException(String.format(MESSAGE_error_substance_twice_in_left, subName));
            }
            leftSubstances.add(substance);
        }

        String[] rightPart = text.substring(posArrow+2, text.length()).split("\\+");
        for(String subName : rightPart) {
            cheakAndRefactorSubstance(subName);
            Substance substance = new Substance(subName);
            if(rightSubstances.contains(substance)){
                throw new ParsingEquationException(String.format(MESSAGE_error_substance_twice_in_right, subName));
            }
            if(leftSubstances.contains(substance)){
                throw new ParsingEquationException(String.format(MESSAGE_error_substance_twice_in_both, subName));
            }
            rightSubstances.add(substance);
        }

        checkPresenceOfElementsInBothPartOfEquation(leftSubstances, rightSubstances);

        RationalMatrix equationSystemMatrix = createRationalMatrix(leftSubstances, rightSubstances);
        long[][] solution = equationSystemMatrix.solveEquationSystem();

        checkAndEditSigns(solution);

        return messageAboutSolution(leftSubstances, rightSubstances, solution);
    }

    private static void printLongMas( final long[][] solution){
            for(long[] row :solution){
            for(long val : row){
                System.out.print(val + ", ");
            }
            System.out.println();
        }
    }

    private static String messageAboutSolution(final Set<Substance> leftSubstances, final Set<Substance> rightSubstances, final long[][] solution)
            throws ParsingEquationException {
        List<Substance> leftSubstancesList = new ArrayList<>();
        leftSubstancesList.addAll(leftSubstances);
        List<Substance> rightSubstancesList = new ArrayList<>();
        rightSubstancesList.addAll(rightSubstances);

        int numOfSubstances = leftSubstancesList.size() + rightSubstancesList.size();
        for(long[] row : solution){
            if (row.length != numOfSubstances)
                throw new ParsingEquationException(MESSAGE_critical_error);
        }
        if(solution.length == 0)
            return MESSAGE_no_solutions_of_the_equation;

        List<Integer> answersIndexes = new ArrayList<>(); /*Indexes of solutions for answer*/
        List<Integer> supposeIndexes = new ArrayList<>(); /*Indexes of solutions for offer to change equation */

        for(int i = 0; i < solution.length; ++i){
            long[] row = solution[i];
            boolean isAnswer = false;
            for(long val : row){
                if(val > 0){
                    isAnswer = true;
                }else if(val < 0){
                    supposeIndexes.add(i);
                    isAnswer = false;
                    break;
                }
            }
            if(isAnswer)
                answersIndexes.add(i);
        }

        StringBuilder message = new StringBuilder();

        addToMessageAboutFindedAnswers(message,
                answersIndexes,
                leftSubstancesList,
                rightSubstancesList,
                solution);

        addToMessageAboutSupposedAnswers(message,
                supposeIndexes,
                leftSubstancesList,
                rightSubstancesList,
                solution);

        return message.toString();
    }

    private static void addToMessageAboutFindedAnswers(StringBuilder message,
                                   final List<Integer> answersIndexes,
                                   final List<Substance> leftSubstancesList,
                                   final List<Substance> rightSubstancesList,
                                   final long[][] solution) {
        if (answersIndexes.size() > 0) {
            if (answersIndexes.size() == 1)
                message.append(MESSAGE_the_only_solution_found);
            else
                message.append(MESSAGE_common_solution_is_a_linear_combination);
            for (int i : answersIndexes) {
                boolean isFirst = true;
                for (int j = 0; j < leftSubstancesList.size(); ++j) {
                    if (solution[i][j] != 0) {
                        if (!isFirst)
                            message.append(" + ");
                        isFirst = false;
                        long koeff = solution[i][j];
                        if (koeff != 1)
                            message.append(koeff);
                        message.append(leftSubstancesList.get(j).getName());
                    }
                }
                message.append(" " + MESSAGE_CULTIVATOR + " ");
                isFirst = true;
                for (int j = 0; j < rightSubstancesList.size(); ++j) {
                    int secondIndex = leftSubstancesList.size() + j;
                    if (solution[i][secondIndex] != 0) {
                        if (!isFirst)
                            message.append(" + ");
                        isFirst = false;
                        long koeff = solution[i][secondIndex];
                        if (koeff != 1)
                            message.append(koeff);
                        message.append(rightSubstancesList.get(j).getName());
                    }
                }
                message.append("\n");
            }
        }
    }

    private static void addToMessageAboutSupposedAnswers(StringBuilder message,
                                                         final List<Integer> supposeIndexes,
                                                         final List<Substance> leftSubstancesList,
                                                         final List<Substance> rightSubstancesList,
                                                         final long[][] solution) {
        if (supposeIndexes.size() > 0) {
            message.append(MESSAGE_the_solution_may_have_been_written_incorrectly);
            int counter = 0;
            for (int i : supposeIndexes) {
                if (supposeIndexes.size() > 1) {
                    message.append(MESSAGE_version + (++counter) + ". ");
                }
                List<Integer> leftIndexes = new ArrayList<>();
                List<Integer> rightIndexes = new ArrayList<>();
                for (int j = 0; j < leftSubstancesList.size(); ++j)
                    if (solution[i][j] < 0)
                        leftIndexes.add(j);
                for (int j = 0; j < rightSubstancesList.size(); ++j)
                    if (solution[i][leftSubstancesList.size() + j] < 0)
                        rightIndexes.add(j);

                if (leftIndexes.size() > 0) {
                    message.append(MESSAGE_to_obtain_additional_solutions_move_from_the_left_to_the_right);
                    if (leftIndexes.size() == 1) {
                        message.append(MESSAGE_substance);
                        message.append(leftSubstancesList.get(leftIndexes.get(0)).getName());
                    } else {
                        message.append(MESSAGE_substances);
                        for (int j = 0; j < leftIndexes.size(); ++j) {
                            message.append(leftSubstancesList.get(leftIndexes.get(j)).getName());
                            if (j < leftIndexes.size() - 1)
                                message.append(", ");
                        }
                    }
                    message.append(". ");
                }

                if (rightIndexes.size() > 0) {
                    if (leftIndexes.size() != 0)
                        message.append(MESSAGE_also_move_from_the_right_ti_the_left);
                    else
                        message.append(MESSAGE_to_obtain_additional_solutions_move_from_the_right_to_the_leftt);
                    if (rightIndexes.size() == 1) {
                        message.append(MESSAGE_substance);
                        message.append(rightSubstancesList.get(rightIndexes.get(0)).getName());
                    } else {
                        message.append(MESSAGE_substances);
                        for (int j = 0; j < rightIndexes.size(); ++j) {
                            message.append(rightSubstancesList.get(rightIndexes.get(j)).getName());
                            if (j < rightIndexes.size() - 1)
                                message.append(", ");
                        }
                    }
                    message.append(". ");
                }
            }
        }
    }

    private static void checkAndEditSigns(long[][] solution){
        for(long[] row: solution) {
            int plusCounter = 0;
            int notZero = 0;
            for(long val : row){
                if(val > 0){
                    ++plusCounter;
                    ++notZero;
                }else if(val < 0){
                    --plusCounter;
                    ++notZero;
                }
            }
            if(notZero != 0 && plusCounter * 2 < notZero){
                /*Changing sign in row*/
                for(int i = 0; i < row.length; ++i)
                    row[i] = -row[i];
            }
        }
    }

    private static void cheakAndRefactorSubstance(String subName) throws ParsingEquationException {
        subName = subName.trim().toLowerCase();
        if (!subName.matches("^(" + ELEMENT_PATTERN + "\\.)*" + ELEMENT_PATTERN + "$"))
            throw new ParsingEquationException(String.format(MESSAGE_error_the_word_is_bad_for_a_name_of_substance, subName));
    }

    private static void checkPresenceOfElementsInBothPartOfEquation(final Set<Substance> leftSubstances, final Set<Substance> rightSubstances)
            throws ParsingEquationException {
        Set<Element> leftElements  = new TreeSet<>();
        Set<Element> rightElements = new TreeSet<>();

        for(Substance substance : leftSubstances)
            leftElements.addAll(substance.getElementsSet());

        for(Substance substance : rightSubstances)
            rightElements.addAll(substance.getElementsSet());

        boolean isEqual = false;
        if( leftElements.size() == rightElements.size() ){
            isEqual = true;
            for( Element element : leftElements ){
                if(!rightElements.contains(element)){
                    isEqual = false;
                    break;
                }
            }
        }
        if(!isEqual){
            ArrayList<Element> leftList = new ArrayList<>();
            ArrayList<Element> rightList = new ArrayList<>();
            for(Element element : leftElements){
                if(!rightElements.contains(element))
                    leftList.add(element);
            }
            for(Element element : rightElements){
                if(!leftElements.contains(element))
                    rightList.add(element);
            }
            StringBuilder messageBuilder = new StringBuilder(MESSAGE_error);
            if (leftList.size() > 0){
                if (leftList.size() == 1)
                    messageBuilder.append(MESSAGE_Element);
                else
                    messageBuilder.append(MESSAGE_Elements);

                for(int i = 0; i < leftList.size(); ++i){
                    messageBuilder.append(leftList.get(i).getName());
                    if(i != leftList.size() - 1)
                        messageBuilder.append(", ");
                    else
                        messageBuilder.append(" ");
                }
                if (leftList.size() == 1)
                    messageBuilder.append(MESSAGE_is_present_in_the_left_part_of_the_equation_but_it_is_not_present_in_the_right_part_of_the_equation);
                else
                    messageBuilder.append(MESSAGE_are_present_in_the_left_part_of_the_equation_but_they_are_not_present_in_the_right_part_of_the_equation);
            }
            if (rightList.size() > 0){
                if (rightList.size() == 1)
                    messageBuilder.append(MESSAGE_Element);
                else
                    messageBuilder.append(MESSAGE_Elements);

                for(int i = 0; i < rightList.size(); ++i){
                    messageBuilder.append(rightList.get(i).getName());
                    if(i != rightList.size() - 1)
                        messageBuilder.append(", ");
                    else
                        messageBuilder.append(" ");
                }
                if (rightList.size() == 1)
                    messageBuilder.append(MESSAGE_is_present_in_the_right_part_of_the_equation_but_it_is_not_present_in_the_left_part_of_the_equation);
                else
                    messageBuilder.append(MESSAGE_are_present_in_the_right_part_of_the_equation_but_they_are_not_present_in_the_left_part_of_the_equation);
            }
            throw new ParsingEquationException(messageBuilder.toString());
        }
    }

    private static RationalMatrix createRationalMatrix(final Set<Substance> leftSubstances, final Set<Substance> rightSubstances){
        Set<Element> allElements = new TreeSet<>();
            for(Substance substance : leftSubstances)
                allElements.addAll(substance.getElementsSet());

            for(Substance substance : rightSubstances)
                allElements.addAll(substance.getElementsSet());

        int rowNum = allElements.size();
        int colNum = leftSubstances.size() + rightSubstances.size();
        Rational[][] matrix = new Rational[rowNum][colNum];

        int i = 0;
            for(Element element : allElements){
            int j = 0;
            for(Substance substance : leftSubstances){
                if(substance.getElementsSet().contains(element)){
                    matrix[i][j] = new Rational(substance.getElementsWithIndexes().get(element));
                }else{
                    matrix[i][j] = new Rational(0);
                }
                ++j;
            }
            for(Substance substance : rightSubstances){
                if(substance.getElementsSet().contains(element)){
                    matrix[i][j] = new Rational( -1 * substance.getElementsWithIndexes().get(element) );
                }else{
                    matrix[i][j] = new Rational(0);
                }
                ++j;
            }
            ++i;
        }

        RationalMatrix rationalMatrix = new RationalMatrix(matrix);

//        rationalMatrix.printMatr();
//        for(Element element : allElements) {
//            System.out.println(element.getName());
//        }
//        for(Substance substance : leftSubstances) {
//            System.out.print(substance.getName() + " ");
//        }
//        for(Substance substance : rightSubstances) {
//            System.out.print(substance.getName() + " ");
//        }

        return rationalMatrix;

    }

}

