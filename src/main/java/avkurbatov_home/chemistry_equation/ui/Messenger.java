package avkurbatov_home.chemistry_equation.ui;

import avkurbatov_home.chemistry_equation.enums.Language;
import avkurbatov_home.chemistry_equation.substance.Substance;

import java.util.Set;

import static avkurbatov_home.chemistry_equation.constants.Constants.MESSAGE_DELIMITER;
import static avkurbatov_home.chemistry_equation.constants.Messages.*;

/**
 * Messenger is used for building all messages for UI.
 * Not thread-safe
 * */
public class Messenger {

    private Language language = Language.ENGLISH;
    private int tip = 0;
    private int messagesForUserCounter = 0;

    public static final Messenger MESSENGER = new Messenger();

    private Messenger() {}

    public void setLanguage(Language language) {
        this.language = language;
    }

    private String translate(String[] mas){
        if (language.index < mas.length){
            return mas[language.index];
        }
        return mas[0];
    }

     public String calculateCoefficients() {
        return translate(CALCULATE_COEFFICIENTS);
    }

    public String getNextTip() {
        String message = getCurrentTip();
        tip = (tip + 1) % 4;
        return message;
    }

    public String getCurrentTip() {
        return translate(TIPS[tip]);
    }

    public String tipAnswerLanguage() {
        return translate(TIP_ANSWER_LANGUAGE);
    }

    public String getMessageForUser() {
        String[] message = MESSAGES_FOR_USER[messagesForUserCounter];
        if(messagesForUserCounter < MESSAGES_FOR_USER.length) {
            ++messagesForUserCounter;
        }
        return translate(message);
    }

    public String criticalError() {
        return translate(CRITICAL_ERROR);
    }

    public String goodbye() {
        return translate(GOODBYE);
    }

    public String inOutError() {
        return translate(IN_OUT_ERROR);
    }

    public String errorMissedDelimiter() {
        return String.format(translate(ERROR_MISSED_DELIMITER), MESSAGE_DELIMITER);
    }

    public String errorSubstanceTwiceInLeft(Substance substance) {
        return String.format(translate(ERROR_SUBSTANCE_TWICE_IN_LEFT), substance.getName());
    }

    public String errorSubstanceTwiceInRight(Substance substance) {
        return String.format(translate(ERROR_SUBSTANCE_TWICE_IN_RIGHT), substance.getName());
    }

    public String errorSubstanceTwiceInBoth(Set<Substance> bothSidesSubstances) {
        return String.format(translate(ERROR_SUBSTANCE_TWICE_IN_BOTH), bothSidesSubstances);
    }

    public String noSolutionsOfTheEquation() {
        return translate(NO_SOLUTIONS_OF_THE_EQUATION);
    }

    public String theOnlySolutionFound() {
        return translate(THE_ONLY_SOLUTION_FOUND);
    }

    public String commonSolutionIsALinearCombination() {
        return translate(COMMON_SOLUTION_IS_A_LINEAR_COMBINATION);
    }

    public String maybeTheEquationWasSetIncorrectly() {
        return translate(MAYBE_THE_EQUATION_WAS_SET_INCORRECTLY);
    }

    public String version() {
        return translate(VERSION);
    }

    public String toObtainAdditionalSolutionsMoveFromTheLeftToTheRight() {
        return translate(TO_OBTAIN_ADDITIONAL_SOLUTIONS_MOVE_FROM_THE_LEFT_TO_THE_RIGHT);
    }

    public String substance() {
        return translate(SUBSTANCE);
    }

    public String substances() {
        return translate(SUBSTANCES);
    }

    public String alsoMoveFromTheRightToTheLeft() {
        return translate(ALSO_MOVE_FROM_THE_RIGHT_TO_THE_LEFT);
    }

    public String toObtainAdditionalSolutionsMoveFromTheRightToTheLeft() {
        return translate(TO_OBTAIN_ADDITIONAL_SOLUTIONS_MOVE_FROM_THE_RIGHT_TO_THE_LEFT);
    }

    public String errorTheWordIsBadForNameOfSubstance(String substanceName) {
        return String.format(translate(ERROR_THE_WORD_IS_BAD_FOR_A_NAME_OF_SUBSTANCE), substanceName);
    }

    public String error() {
        return translate(ERROR);
    }

    public String element(boolean isInUnitForm) {
        return translate( isInUnitForm? ELEMENT: ELEMENTS);
    }

    public String isPresentOnlyInLeftPart(boolean isInUnitForm) {
        return translate(isInUnitForm? IS_PRESENT_ONLY_IN_LEFT_PART: ARE_PRESENT_ONLY_IN_LEFT_PART);
    }

    public String isPresentOnlyInRightPart(boolean isInUnitForm) {
        return translate(isInUnitForm? IS_PRESENT_ONLY_IN_RIGHT_PART: ARE_PRESENT_ONLY_IN_RIGHT_PART);
    }

    public String errorUnableToGetElementName() {
        return translate(ERROR_UNABLE_TO_GET_ELEMENT_NAME);
    }

    public String errorElementDoesNotExist(String elementName) {
        return String.format(translate(ERROR_ELEMENT_DOES_NOT_EXIST), elementName);
    }

    public String errorInEquation() {
        return translate(ERROR_IN_EQUATION);
    }

    public String chemicalEquationSolverTitle() {
        return translate(CHEMICAL_EQUATION_SOLVER_TITLE);
    }

    public String typeChemicalEquationHere() {
        return translate(TYPE_CHEMICAL_EQUATION_HERE);
    }

    public String tip() {
        return translate(TIP);
    }

    public String nextTip() {
        return translate(NEXT_TIP);
    }

    public String chooseLanguage() {
        return translate(CHOOSE_LANGUAGE);
    }

    public String indexMustBePositive(int index) {
        return String.format(translate(INDEX_MUST_BE_POSITIVE), index);
    }

    public String indexMustBePositiveNumber(String str) {
        return String.format(translate(INDEX_MUST_BE_POSITIVE_NUMBER), str);
    }
}