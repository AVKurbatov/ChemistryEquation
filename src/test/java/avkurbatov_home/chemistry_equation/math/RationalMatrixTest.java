package avkurbatov_home.chemistry_equation.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RationalMatrixTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotValidateNonRectangularMatrix() {
        List<List<Rational>> matrix = Arrays.asList(
                Arrays.asList(Rational.of(0), Rational.of(1)),
                Arrays.asList(Rational.of(0), Rational.of(1), Rational.of(2)));

        new RationalMatrix(matrix);
    }

    @Test
    public void shouldPerformUpperTriangulation() {
        // Given
        List<List<Rational>> matrix = Arrays.asList(
                Arrays.asList(Rational.of(5), Rational.of(1), Rational.of(1)),
                Arrays.asList(Rational.of(3), Rational.of(4), Rational.of(2)));
        RationalMatrix rationalMatrix = new RationalMatrix(matrix);

        // 4 - 3/5 = 17/5, 2 - 3/5 = 7/5
        List<List<Rational>> expectedMatrix = Arrays.asList(
                Arrays.asList(Rational.of(5), Rational.of(1), Rational.of(1)),
                Arrays.asList(Rational.of(0), Rational.of(17,5), Rational.of(7, 5)));
        // When
        rationalMatrix.performUpperTriangulation();
        // Then
        assertEquals(expectedMatrix, rationalMatrix.getMatrix());
    }

    @Test
    public void shouldPerformDiagonalization() {
        // Given
        List<List<Rational>> matrix = Arrays.asList(
                Arrays.asList(Rational.of(5), Rational.of(1), Rational.of(1)),
                Arrays.asList(Rational.of(3), Rational.of(4), Rational.of(2)));
        RationalMatrix rationalMatrix = new RationalMatrix(matrix);
        rationalMatrix.performUpperTriangulation();

        // second row as in shouldPerformUpperTriangulation()
        // 1 - 7/5 * 5/17 = 10/17
        List<List<Rational>> expectedMatrix = Arrays.asList(
                Arrays.asList(Rational.of(5), Rational.of(0), Rational.of(10, 17)),
                Arrays.asList(Rational.of(0), Rational.of(17,5), Rational.of(7, 5)));
        // When
        rationalMatrix.performDiagonalization();
        // Then
        assertEquals(expectedMatrix, rationalMatrix.getMatrix());
    }

}
