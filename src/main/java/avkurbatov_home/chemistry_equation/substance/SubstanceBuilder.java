package avkurbatov_home.chemistry_equation.substance;

import avkurbatov_home.chemistry_equation.constants.Constants;
import avkurbatov_home.chemistry_equation.enums.Element;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

public final class SubstanceBuilder {

    private static final Pattern ELEMENT_REGEX = Pattern.compile(Constants.ELEMENT_PATTERN);

    private SubstanceBuilder() {}

    public static Substance buildByString(String substanceName) throws ParsingEquationException {

        substanceName = substanceName.trim().toLowerCase();
        if (!substanceName.matches("^(" + Constants.ELEMENT_PATTERN + "\\.)*" +
                Constants.ELEMENT_PATTERN + "$")) {
            throw new ParsingEquationException(MESSENGER.errorTheWordIsBadForNameOfSubstance(substanceName));
        }

        Map<Element, Integer> elementToIndex = new LinkedHashMap<>(); // LinkedHashSet used to save order
        StringBuilder nameBuilder = new StringBuilder("");

        Matcher regexMatcher = ELEMENT_REGEX.matcher(substanceName);
        while (regexMatcher.find()) {

            String elementNameWithIndex = regexMatcher.group();
            int posOfSeparator = elementNameWithIndex.indexOf("_");

            Element element = findElement(elementNameWithIndex, posOfSeparator);
            int index = findElementIndex(elementNameWithIndex, posOfSeparator);

            incrementIndexByValue(elementToIndex, element, index);

            appendElementToNameBuilder(nameBuilder, element, index);
        }

        return new Substance(nameBuilder.toString(), elementToIndex);
    }

    private static int findElementIndex(String elementNameWithIndex, int posOfSeparator) throws ParsingEquationException {
        if (posOfSeparator == -1) {
            return 1;
        } else {
            return parseInt(elementNameWithIndex.substring(posOfSeparator + 1));
        }
    }

    private static Element findElement(String elementNameWithIndex, int posOfSeparator) throws ParsingEquationException {
        if (posOfSeparator == -1) {
            return Element.of(elementNameWithIndex);
        } else {
            return Element.of(elementNameWithIndex.substring(0, posOfSeparator));
        }
    }

    private static int parseInt(String str) throws ParsingEquationException {
        try {
            int index = Integer.parseInt(str);
            if (index > 0) {
                return index;
            }
            throw new ParsingEquationException(MESSENGER.indexMustBePositive(index));
        } catch (NumberFormatException e) {
            throw new ParsingEquationException(MESSENGER.indexMustBePositiveNumber(str), e);
        }
    }

    private static void incrementIndexByValue(Map<Element, Integer> elementToIndex, Element element, int value) {
        if (elementToIndex.containsKey(element)) {
            elementToIndex.put(element, elementToIndex.get(element) + 1);
        } else {
            elementToIndex.put(element, value);
        }
    }

    private static void appendElementToNameBuilder(StringBuilder nameBuilder, Element element, int index) {
        if (nameBuilder.length() != 0) {
            nameBuilder.append(".");
        }
        nameBuilder.append(element.toString());
        if (index > 1) {
            nameBuilder.append("_");
            nameBuilder.append(index);
        }
    }
}
