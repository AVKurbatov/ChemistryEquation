package chemistry_equation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Александр on 19.09.2017.
 */
public class RationalTest {
    @Test
    void test1(){
        Rational x = new Rational(-4, -12);
        assertTrue(x.equals(new Rational(1,3)));
    }
    @Test
    void test2(){
        Rational x = new Rational(-4, -12);
        Rational y = new Rational(3, 2);
        Rational z = x.multiply(y);
        assertTrue(z.equals(new Rational(2,4)));
    }


}