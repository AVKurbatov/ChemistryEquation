package avkurbatov_home.chemistry_equation.math;

import avkurbatov_home.chemistry_equation.enums.Element;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;
import avkurbatov_home.chemistry_equation.substance.Substance;
import avkurbatov_home.chemistry_equation.substance.SubstanceBuilder;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static avkurbatov_home.chemistry_equation.constants.Constants.MESSAGE_DELIMITER;
import static org.junit.Assert.*;

public class EquationSolverTest {

    private final EquationSolver equationSolver = new EquationSolver();

    // region findSubstancesInEquationPart tests
    @Test
    public void shouldFindSubstancesInLeftEquationPart() {
        // Given
        final String equation = "Fe.O + H_2.S.O_4 -> H_2.O + Fe.H.S.O_4";
        // When
        try {
            List<Substance> substances = equationSolver.findSubstancesInEquationPart(equation, true);
            // Then
            assertEquals(Arrays.asList(SubstanceBuilder.buildByString("Fe.O"),
                    SubstanceBuilder.buildByString("H_2.S.O_4")), substances);
        } catch (ParsingEquationException e){
            fail("should find all substances in left equation part, equation: " + equation +
                    ", Exception: " + e.toString());
        }
    }

    @Test
    public void shouldFindSubstancesInRightEquationPart() {
        // Given
        final String equation = "Fe.O + H_2.S.O_4 -> H_2.O + Fe.H.S.O_4";
        // When
        try {
            List<Substance> substances = equationSolver.findSubstancesInEquationPart(equation, false);
            // Then
            assertEquals(Arrays.asList(SubstanceBuilder.buildByString("H_2.O"),
                    SubstanceBuilder.buildByString("Fe.H.S.O_4")), substances);
        } catch (ParsingEquationException e){
            fail("should find all substances in right equation part, equation: " + equation +
                    ", Exception: " + e.toString());
        }
    }

    // endregion

    // region positive solveChemistryEquation tests
    @Test
    public void shouldFindOneSolution() throws Exception {
        assertEquals("The only solution found:\n2Na.O.H + H_2.S.O_4 -> Na_2.S.O_4 + 2H_2.O\n",
                equationSolver.solveChemistryEquation("na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o"));
    }

    @Test
    public void shouldFindSeveralSolutions() throws Exception {
        assertEquals("Common solution is a linear combination of the following particular solutions:\n" +
                "2C + O_2 -> 2C.O\n" +
                "C + O_2 -> C.O_2\n", equationSolver.solveChemistryEquation("c + o_2 -> c.o + c.o_2"));
    }

    @Test
    public void shouldGiveAdditionalAdvice() throws Exception {
        assertEquals("The only solution found:\n" +
                "2C + O_2 -> 2C.O\n" +
                "Maybe the equation was set incorrectly\n" +
                "To obtain additional solutions, move from the left part of the equation to the right substances C, O_2.",
                equationSolver.solveChemistryEquation("c + o_2 + c.o_2 -> c.o"));
    }

    @Test
    public void shouldFindNoSolutions() throws Exception {
        assertEquals("There are no solutions of the equation. Check equation.",
                equationSolver.solveChemistryEquation("c.o_2 -> c.o"));
    }
    // endregion

    // region solveChemistryEquation tests with invalid equations
    @Test
    public void shouldFailAndExpectDelimiter() {
        shouldThrowParsingEquationException("c + o_2 + c.o_2", MESSAGE_DELIMITER);
    }

    @Test
    public void shouldFailDueToUnexpectedElement() {
        shouldThrowParsingEquationException("c + om_2 -> c.om_2",
                "The element \"om\" does not exist");
    }

    @Test
    public void shouldFailDueSubstanceOccursInBothPartOfEquation() {
        shouldThrowParsingEquationException("c + o_2 -> c + c.o_2",
                "Substances with names \"[C]\" occur in both parts of the equation!");
    }

    @Test
    public void shouldFailDueSubstanceOccursTwiceInLeftPart() {
        shouldThrowParsingEquationException("c + o_2 + c -> c.o_2",
                "A substance with a name \"C\" occurs twice in the left part of the equation!");
    }

    @Test
    public void shouldFailDueSubstanceOccursTwiceInRightPart() {
        shouldThrowParsingEquationException("c + o_2 -> c.o_2 + c.o_2",
                "Error! A substance with a name \"C.O_2\" occurs twice in the right part of the equation");
    }

    @Test
    public void shouldFailDueElementIsPresentOnlyInLeftPart() {
        shouldThrowParsingEquationException("c + o + cl_2 -> o_2.cl",
                "Element \"C\" is present in the left part of the equation, " +
                        "but it is not present in the right part of the equation");
    }

    @Test
    public void shouldFailDueElementIsPresentOnlyInRightPart() {
        shouldThrowParsingEquationException("c + o_2 -> c.o.cl_2",
                "Element \"Cl\" is present in the right part of the equation, but it is not present in the left part of the equation");
    }
    // endregion

    // region checkPresenceOfElementsInBothPartsOfEquation tests
    @Test
    public void shouldDecideThatElementListsAreIdentical() {
        // Given
        Substance substance1 = null;
        Substance substance2 = null;
        Substance substance3 = null;
        Substance substance4 = null;
        try {
            substance1 = SubstanceBuilder.buildByString("c_2.cl");
            substance2 = SubstanceBuilder.buildByString("Ar.Zr_5");

            substance3 = SubstanceBuilder.buildByString("zr.c");
            substance4 = SubstanceBuilder.buildByString("Cl.Ar");
        } catch (ParsingEquationException e) {
            fail("Check arguments in buildByString(). Exception: " + e);
        }
        final List<Substance> leftSubstances = Arrays.asList(substance1, substance2);
        final List<Substance> rightSubstances = Arrays.asList(substance3, substance4);

        try {
            // When
            equationSolver.checkPresenceOfElementsInBothPartsOfEquation(leftSubstances, rightSubstances);
            // Then
        } catch (ParsingEquationException e) {
            fail("Method should decide that lists of elements are Identical. Exception: " + e);
        }
    }

    @Test
    public void shouldDecideThatElementListsAreNotIdentical() {
        // Given
        Substance substance1 = null;
        Substance substance2 = null;
        Substance substance3 = null;
        Substance substance4 = null;
        try {
            substance1 = SubstanceBuilder.buildByString("c_2.cl");
            substance2 = SubstanceBuilder.buildByString("Ar.Zr_5");

            substance3 = SubstanceBuilder.buildByString("c");
            substance4 = SubstanceBuilder.buildByString("Cl.Ar");
        } catch (ParsingEquationException e) {
            fail("Check arguments in buildByString(). Exception: " + e);
        }
        final List<Substance> leftSubstances = Arrays.asList(substance1, substance2);
        final List<Substance> rightSubstances = Arrays.asList(substance3, substance4);

        try {
            // When
            equationSolver.checkPresenceOfElementsInBothPartsOfEquation(leftSubstances, rightSubstances);
            // Then
            fail("Method should decide that lists of elements are not Identical.");
        } catch (ParsingEquationException e) {
            assertTrue(StringUtils.containsIgnoreCase(e.toString(),
                    "Element \"Zr\" is present in the left part of the equation, " +
                            "but it is not present in the right part of the equation."));
        }
    }
    // endregion

    // region appendElementNames tests
    @Test
    public void shouldAppendElementNames() {
        // Given
        final StringBuilder messageBuilder = new StringBuilder();
        final Set<Element> set = new LinkedHashSet<>();
        set.add(Element.Li);
        set.add(Element.Ar);
        set.add(Element.C);
        // When
        equationSolver.appendElementNames(messageBuilder, set);
        // Then
        assertEquals("\"Li\", \"Ar\", \"C\" ", messageBuilder.toString());
    }

    @Test
    public void shouldAppendElementNamesWithEmptyElementSet() {
        // Given
        final StringBuilder messageBuilder = new StringBuilder();
        // When
        equationSolver.appendElementNames(messageBuilder, Collections.emptySet());
        // Then
        assertEquals("", messageBuilder.toString());
    }
    // endregion

    // region createRationalMatrix tests
    @Test
    public void shouldCreateCorrectRationalMatrix() {
        // Given
        List<Substance> leftSubstances = null;
        List<Substance> rightSubstances = null;

        try {
            leftSubstances = Arrays.asList(SubstanceBuilder.buildByString("c_2.ar"),
                    SubstanceBuilder.buildByString("c_2.o_3"));
            rightSubstances = Arrays.asList(SubstanceBuilder.buildByString("ar_2"),
                    SubstanceBuilder.buildByString("c.o_2"));
        } catch (ParsingEquationException e) {
            fail("Check arguments in buildByString(). Exception: " + e);
        }
        final List<Rational> indexesOfElementC = Arrays.asList(Rational.of(2),
                                                                Rational.of(2),
                                                                Rational.of(0),
                                                                Rational.of(-1));
        final List<Rational> indexesOfElementAr = Arrays.asList(Rational.of(1),
                                                                Rational.of(0),
                                                                Rational.of(-2),
                                                                Rational.of(0));
        final List<Rational> indexesOfElementO = Arrays.asList(Rational.of(0),
                                                                Rational.of(3),
                                                                Rational.of(0),
                                                                Rational.of(-2));
        final Set<List<Rational>> expectedSetOfIndexes =
                ImmutableSet.of(indexesOfElementC, indexesOfElementAr, indexesOfElementO);

        // When
        RationalMatrix rationalMatrix = equationSolver.createRationalMatrix(leftSubstances, rightSubstances);

        // Then
        // We check equality of sets because order doesn't make sense
        assertEquals(expectedSetOfIndexes, new HashSet<>(rationalMatrix.getMatrix()));

        assertEquals(Arrays.asList(0, 1, 2, 3), rationalMatrix.getCurrentOrderOfColumns());
        assertEquals(4, rationalMatrix.getColsNumber()); // there are 4 substances
        assertEquals(3, rationalMatrix.getRowsNumber()); // there are 3 Elements
    }
    // endregion

    private void shouldThrowParsingEquationException(String equation, String expectedMessage) {
        try {
            System.out.println(equationSolver.solveChemistryEquation(equation));
            fail("Must throw ParsingEquationException");
        } catch (ParsingEquationException e) {
            assertTrue("Exception message \"" + e.toString() + "\" must contain message " + expectedMessage,
                    StringUtils.containsIgnoreCase(e.toString(), expectedMessage));
        } catch (Exception e) {
            fail("Must throw ParsingEquationException");
        }
    }

}
