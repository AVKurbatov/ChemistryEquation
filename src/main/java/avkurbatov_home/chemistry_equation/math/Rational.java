package avkurbatov_home.chemistry_equation.math;

/**
 * Rational number class.
 * Immutable.
 * To create values use factory methods of()
 * */
public class Rational {
    private final long numerator;
    private final long denominator;

    public long getNumerator() {  return numerator;  }

    public long getDenominator() {  return denominator;  }

    private Rational(long numerator, long denominator){
        if (denominator <= 0) {
            throw new ArithmeticException("Denominator must be positive");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Rational of(long numerator) {
        return of(numerator, 1);
    }

    public static Rational of(long numerator, long denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must not be equal to 0");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        if (numerator == 0) {
            denominator = 1;
        } else {
            long factor = 2;

            while (factor <= Math.min(Math.abs(numerator), Math.abs(denominator))) {
                if (numerator % factor == 0 && denominator % factor == 0) {
                    numerator /= factor;
                    denominator /= factor;
                } else {
                    ++factor;
                }
            }
        }

        return new Rational(numerator, denominator);
    }

    /**
     * @return sum of two rational values
     * */
    public Rational plus(Rational r){
        long newNumerator = numerator * r.denominator + denominator * r.numerator;
        long newDenominator = denominator * r.denominator;

        return Rational.of(newNumerator, newDenominator);
    }

    /**
     * @return the rational value with opposite sign
     * */
    public Rational changeSign(){
        return Rational.of(-numerator, denominator);
    }

    /**
     * @return difference of two rational values
     * */
    public Rational minus(Rational r){
        return this.plus(r.changeSign());
    }

    /**
     * @return product of two rational values
     * */
    public Rational multiply(Rational r){
        long newNumerator = numerator * r.numerator;
        long newDenominator = denominator * r.denominator;

        return Rational.of(newNumerator, newDenominator);
    }

    /**
     * @return quotient of two rational values
     * */
    public Rational divide(Rational r){
        long newNumerator = numerator * r.denominator;
        long newDenominator = denominator * r.numerator;

        return Rational.of(newNumerator, newDenominator);
    }

    public boolean notEqualsZero() {
        return !equalsZero();
    }

    public boolean equalsZero() {
        return getNumerator() == 0;
    }

    @Override
    public String toString(){
        return Long.toString(numerator) + "/" + denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rational rational = (Rational) o;

        if (numerator != rational.numerator) return false;
        return denominator == rational.denominator;
    }

    @Override
    public int hashCode() {
        int result = (int) (numerator ^ (numerator >>> 32));
        result = 31 * result + (int) (denominator ^ (denominator >>> 32));
        return result;
    }

}
