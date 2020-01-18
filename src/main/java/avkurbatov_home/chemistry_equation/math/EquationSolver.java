package avkurbatov_home.chemistry_equation.math;

import java.util.*;
import java.util.stream.Collectors;

import avkurbatov_home.chemistry_equation.substance.Substance;
import avkurbatov_home.chemistry_equation.enums.Element;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;
import avkurbatov_home.chemistry_equation.substance.SubstanceBuilder;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

import static avkurbatov_home.chemistry_equation.constants.Constants.MESSAGE_DELIMITER;
import static avkurbatov_home.chemistry_equation.constants.Constants.PLUS_PATTERN;
import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

public class EquationSolver {

     public String solveChemistryEquation(String equation) throws ParsingEquationException {

         final List<Substance> leftSubstances = findSubstancesInEquationPart(equation, true);
         final List<Substance> rightSubstances = findSubstancesInEquationPart(equation, false);

         checkPresenceOfSubstancesInBothPartsOfEquation(leftSubstances, rightSubstances);
         checkPresenceOfElementsInBothPartsOfEquation(leftSubstances, rightSubstances);

         final RationalMatrix equationSystemMatrix = createRationalMatrix(leftSubstances, rightSubstances);
         final List<List<Long>> solutions = equationSystemMatrix.solveEquationSystem();
         return createAnswerFromSolutions(leftSubstances, rightSubstances, solutions);
    }

    List<Substance> findSubstancesInEquationPart(String equation, boolean isPartLeft)
                                        throws ParsingEquationException {
        final List<Substance> substances = new ArrayList<>();

        final int posArrow = equation.indexOf(MESSAGE_DELIMITER);
        if (posArrow == -1) {
            throw new ParsingEquationException(MESSENGER.errorMissedDelimiter());
        }

        final String[] equationPart;
        if (isPartLeft) {
            equationPart = equation.substring(0, posArrow).split(PLUS_PATTERN);
        } else {
            equationPart = equation.substring(posArrow + MESSAGE_DELIMITER.length(), equation.length()).split(PLUS_PATTERN);
        }
        for (String substanceName : equationPart) {
            final Substance substance = SubstanceBuilder.buildByString(substanceName);
            if (substances.contains(substance)) {
                if (isPartLeft) {
                    throw new ParsingEquationException(MESSENGER.errorSubstanceTwiceInLeft(substance));
                } else {
                    throw new ParsingEquationException(MESSENGER.errorSubstanceTwiceInRight(substance));
                }
            }
            substances.add(substance);
        }
        return substances;
    }

    /**
     * @Returns a message for the user with a solution of the equation
     * */
    private String createAnswerFromSolutions(final List<Substance> leftSubstances, final List<Substance> rightSubstances,
                                             final List<List<Long>> solutions)
                                throws ParsingEquationException {

        checkSolutionsRowsSize(leftSubstances, rightSubstances, solutions);

        if(solutions.size() == 0) {
            return MESSENGER.noSolutionsOfTheEquation();
        }

        List<List<Long>> answerSolutions = new ArrayList<>();
        List<List<Long>> supposedSolutions = new ArrayList<>();

        sortSolutions(answerSolutions, supposedSolutions, solutions);

        StringBuilder message = new StringBuilder();

        appendFoundSolutions(message, answerSolutions, leftSubstances, rightSubstances);

        appendSupposedSolutions(message, supposedSolutions, leftSubstances, rightSubstances);

        return message.toString();
    }

    /**
     * Solver appends all solutions which formally correspond to the chemical sense.
     * */
    private void appendFoundSolutions(final StringBuilder message,
                                      final List<List<Long>> answerSolutions,
                                      final List<Substance> leftSubstances,
                                      final List<Substance> rightSubstances) {
        if (answerSolutions.isEmpty()) {
            return;
        }
        if (answerSolutions.size() == 1) {
            message.append(MESSENGER.theOnlySolutionFound());
        } else {
            message.append(MESSENGER.commonSolutionIsALinearCombination());
        }
        answerSolutions.forEach(solution -> {
            appendLeftPartSubstances(message, leftSubstances, solution);
            message.append(" " + MESSAGE_DELIMITER + " ");

            appendRightPartSubstances(message, leftSubstances.size(), rightSubstances, solution);
            message.append("\n");
        });
    }

    private void appendLeftPartSubstances(final StringBuilder message, final List<Substance> leftSubstances,
                                          final List<Long> solution) {
        final MutableBoolean isFirst = new MutableBoolean(true);
        for (int substanceCounter = 0; substanceCounter < leftSubstances.size(); ++substanceCounter) {
            appendSubstance(message, leftSubstances.get(substanceCounter),
                    solution.get(substanceCounter), isFirst);
        }
    }

    private void appendRightPartSubstances(final StringBuilder message, final int leftSubstancesSize,
                                           final List<Substance> rightSubstances, final List<Long> solution) {
        final MutableBoolean isFirst = new MutableBoolean(true);
        for (int substanceCounter = 0; substanceCounter < rightSubstances.size(); ++substanceCounter) {
            int coefficientCounter = leftSubstancesSize + substanceCounter;
            appendSubstance(message, rightSubstances.get(substanceCounter),
                    solution.get(coefficientCounter), isFirst);
        }
    }

    private void appendSubstance(final StringBuilder message, final Substance substance,
                                 final long coefficient, final MutableBoolean isFirst) {
        if (coefficient == 0) {
            return;
        }
        if (isFirst.isFalse()) {
            message.append(" + ");
        }
        isFirst.setFalse();
        if (coefficient != 1) {
            message.append(coefficient);
        }
        message.append(substance.getName());
    }

    /**
     * Solver can suppose that equation can go in another way. So all supposed solutions are added here.
     * */
    private void appendSupposedSolutions(final StringBuilder message,
                                         final List<List<Long>> supposedSolutions,
                                         final List<Substance> leftSubstances,
                                         final List<Substance> rightSubstances) {
        if (supposedSolutions.isEmpty()) {
            return;
        }
        message.append(MESSENGER.maybeTheEquationWasSetIncorrectly());
        final MutableInt counter = new MutableInt(0);
        supposedSolutions.forEach(solution -> {
            if (supposedSolutions.size() > 1) {
                message.append(MESSENGER.version());
                message.append(Integer.toString(counter.incrementAndGet()));
                message.append(". ");
            }
            final List<Integer> leftIndexes = findLeftIndexes(leftSubstances, solution);

            final List<Integer> rightIndexes = findRightIndexes(leftSubstances.size(), rightSubstances, solution);

            if (leftIndexes.size() > 0) {
                message.append(MESSENGER.toObtainAdditionalSolutionsMoveFromTheLeftToTheRight());

                appendSupposedAnswerSubstance(message, leftIndexes, leftSubstances);
            }

            if (rightIndexes.size() > 0) {
                if (leftIndexes.size() != 0) {
                    message.append(MESSENGER.alsoMoveFromTheRightToTheLeft());
                } else {
                    message.append(MESSENGER.toObtainAdditionalSolutionsMoveFromTheRightToTheLeft());
                }

                appendSupposedAnswerSubstance(message, rightIndexes, rightSubstances);
            }
        });
    }

    private List<Integer> findLeftIndexes(final List<Substance> leftSubstances, final List<Long> solution) {
        List<Integer> leftIndexes = new ArrayList<>();
        for (int substanceCounter = 0; substanceCounter < leftSubstances.size(); ++substanceCounter) {
            if (solution.get(substanceCounter) < 0) {
                leftIndexes.add(substanceCounter);
            }
        }
        return leftIndexes;
    }

    private List<Integer> findRightIndexes(final int leftSubstancesSize, final List<Substance> rightSubstances,
                                           final List<Long> solution) {
        List<Integer> rightIndexes = new ArrayList<>();
        for (int substanceCounter = 0; substanceCounter < rightSubstances.size(); ++substanceCounter) {
            if (solution.get(leftSubstancesSize + substanceCounter) < 0) {
                rightIndexes.add(substanceCounter);
            }
        }
        return rightIndexes;
    }

    private void appendSupposedAnswerSubstance(final StringBuilder message, final List<Integer> indexes,
                                               final List<Substance> substances) {
        if (indexes.size() == 1) {
            message.append(MESSENGER.substance());
            message.append(substances.get(indexes.get(0)).getName());
        } else {
            message.append(MESSENGER.substances());
            for (int j = 0; j < indexes.size(); ++j) {
                message.append(substances.get(indexes.get(j)).getName());
                if (j < indexes.size() - 1) {
                    message.append(", ");
                }
            }
        }
        message.append(".");
    }

    /**
     * @throws ParsingEquationException if sets leftSubstances and rightSubstances has at least one common element
     * */
    private void checkPresenceOfSubstancesInBothPartsOfEquation(final List<Substance> leftSubstances,
                                                                final List<Substance> rightSubstances)
                                    throws ParsingEquationException {
        final Set<Substance> bothSidesSubstances =
                leftSubstances.stream().filter(rightSubstances::contains).collect(Collectors.toSet());
        if (!bothSidesSubstances.isEmpty()) {
            throw new ParsingEquationException(MESSENGER.errorSubstanceTwiceInBoth(bothSidesSubstances));
        }
    }

    /**
     * @throws ParsingEquationException if sets of elements in two parts of equation are not identical
     * */
    void checkPresenceOfElementsInBothPartsOfEquation(final List<Substance> leftSubstances,
                                                      final List<Substance> rightSubstances)
            throws ParsingEquationException {

        final Set<Element> leftElements = collectElementsFromSubstances(leftSubstances);
        final Set<Element> rightElements = collectElementsFromSubstances(rightSubstances);

        if (leftElements.equals(rightElements)) {
            return;
        }

        removeCommonElements(leftElements, rightElements);

        final StringBuilder messageBuilder = new StringBuilder(MESSENGER.error());
        if (leftElements.size() > 0){
            messageBuilder.append(MESSENGER.element(leftElements.size() == 1));
            appendElementNames(messageBuilder, leftElements);
            messageBuilder.append(MESSENGER.isPresentOnlyInLeftPart(leftElements.size() == 1));
        }
        if (rightElements.size() > 0){
            messageBuilder.append(MESSENGER.element(rightElements.size() == 1));
            appendElementNames(messageBuilder, rightElements);
            messageBuilder.append(MESSENGER.isPresentOnlyInRightPart(rightElements.size() == 1));
        }
        throw new ParsingEquationException(messageBuilder.toString());
    }

    /**
     * appends messageBuilder with names of Elements from set
     * */
    void appendElementNames(final StringBuilder messageBuilder, final Set<Element> set) {
        final List<Element> list = new ArrayList<>(set);
        for(int i = 0; i < list.size(); ++i){
            messageBuilder.append("\"");
            messageBuilder.append(list.get(i).toString());
            messageBuilder.append("\"");
            if(i != list.size() - 1) {
                messageBuilder.append(", ");
            } else {
                messageBuilder.append(" ");
            }
        }
    }

    /**
     * @leftSubstances is list of substances in left part of the equation.
     * @rightSubstances is list of substances in right part of the equation.
     * @Returns RationalMatrix with indexes of Elements in substances in the equation.
     * All Rational values have unit denominators.
     * Numerators are equal to index values in substances.
     * They are positive if substance is in left part of equation and negative if in right.
     * One row in RationalMatrix corresponds to one Element in equation.
     * For example, we have equation h_2 + o_2 -> h_2.o.
     * First row corresponds to Element.H and have values [2/1, 0/1, -2/1],
     * second row corresponds to Element.O and have values [0/1, 2/1, -1/1].
     * */
    RationalMatrix createRationalMatrix(final List<Substance> leftSubstances, final List<Substance> rightSubstances) {
        final Set<Element> allElements = new HashSet<>();

        leftSubstances.forEach(substance -> allElements.addAll(substance.getElementsSet()));
        rightSubstances.forEach(substance -> allElements.addAll(substance.getElementsSet()));

        final int rowsNumber = allElements.size();
        final int columnNumber = leftSubstances.size() + rightSubstances.size();
        final List<List<Rational>> matrix = new ArrayList<>(rowsNumber);

        for (Element element : allElements) {
            final List<Rational> row = new ArrayList<>(columnNumber);
            leftSubstances.forEach(substance ->
                    row.add(Rational.of(findIndexOfElementInSubstance(substance, element))));
            rightSubstances.forEach(substance ->
                    row.add(Rational.of(-1L * findIndexOfElementInSubstance(substance, element))));
            matrix.add(row);
        }

        return new RationalMatrix(matrix);
    }

    private Set<Element> collectElementsFromSubstances(final List<Substance> substances) {
        final Set<Element> elements = new HashSet<>();
        for (Substance substance : substances) {
            elements.addAll(substance.getElementsSet());
        }
        return elements;
    }

    private void removeCommonElements(final Set<Element> leftElements, final Set<Element> rightElements) {
        final Set<Element> bothSidesElements =
                rightElements.stream().filter(leftElements::contains).collect(Collectors.toSet());
        leftElements.removeAll(bothSidesElements);
        rightElements.removeAll(bothSidesElements);
    }

    private long findIndexOfElementInSubstance(Substance substance, Element element) {
        Integer index = substance.getElementToIndex().get(element);
        if(index != null) {
            return index.longValue();
        } else {
            return 0L;
        }
    }

    /**
     * @throws ParsingEquationException if any solutions rows size is not equal to full number of substances
     * */
    private void checkSolutionsRowsSize(final List<Substance> leftSubstances, final List<Substance> rightSubstances,
                                        final List<List<Long>> solutions)
            throws ParsingEquationException {
        final int numberOfSubstances = leftSubstances.size() + rightSubstances.size();
        for(List<Long> row : solutions){
            if (row.size() != numberOfSubstances) {
                throw new ParsingEquationException(MESSENGER.criticalError());
            }
        }
    }

    /**
     * All solutions of chemical equations must have non-negative coefficients.
     * We have two types of solutions: real solutions with non-negative coefficients and solutions with one or several
     * negative coefficients. That supposed solutions can take place after moving some substances from
     * one equation part to another.
     * This method sorts solutions into this two types.
     * */
    private void sortSolutions(final List<List<Long>> answerSolutions, final List<List<Long>> supposedSolutions,
                               final List<List<Long>> solutions) {
        solutions.forEach(solution -> {
            if (solution.stream().allMatch(coefficient -> coefficient >= 0)) {
                answerSolutions.add(solution);
            } else {
                supposedSolutions.add(solution);
            }
        });
    }

}

