package avkurbatov_home.chemistry_equation.math;

import avkurbatov_home.chemistry_equation.math.Rational;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class RationalTest {

    @Test
    public void shouldSimplify() {
        Rational x = Rational.of(-4, -12);
        assertTrue(x.equals(Rational.of(1, 3)));
    }

    @Test
    public void plusTest() {
        Rational x = Rational.of(1, 3);
        Rational y = Rational.of(1, 2);
        Rational z = x.plus(y);
        assertTrue(z.equals(Rational.of(5, 6)));
    }

    @Test
    public void minusTest() {
        Rational x = Rational.of(1, 3);
        Rational y = Rational.of(1, 2);
        Rational z = x.minus(y);
        assertTrue(z.equals(Rational.of(-1, 6)));
    }

    @Test
    public void multiplyTest() {
        Rational x = Rational.of(1, -3);
        Rational y = Rational.of(3, 2);
        Rational z = x.multiply(y);
        assertTrue(z.equals(Rational.of(-1, 2)));
    }

    @Test
    public void divideTest() {
        Rational x = Rational.of(1, 3);
        Rational y = Rational.of(2, 3);
        Rational z = x.divide(y);
        assertTrue(z.equals(Rational.of(1, 2)));
    }


}