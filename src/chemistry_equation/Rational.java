package chemistry_equation;

/**
 * Created by Александр on 19.09.2017.
 */
public class Rational {
    //!!! in future: refactor several functions
    private long numer;
    private long denum; /*is always > 0*/

    public long getNumer() {  return numer;  }

    public long getDenum() {  return denum;  }

    public Rational(long numer, long denum){
        if (denum == 0)
            throw new ArithmeticException();
        if (denum > 0){
            this.numer = numer;
            this.denum = denum;
        }else{
            this.numer = -numer;
            this.denum = -denum;
        }
        simplify();
    }

    public Rational(long numer){
        this.numer = numer;
        this.denum = 1;
    }

    private void simplify(){
        if(numer == 0){
            denum = 1;
            return;
        }
        long i = 2;

        long absNumer;
        long signNumer;
        if(numer > 0){
            absNumer = numer;
            signNumer = 1;
        }else{
            absNumer = -numer;
            signNumer = -1;
        }
        long absDenum;
        long signDenum;
        if(denum > 0){
            absDenum = denum;
            signDenum = 1;
        }else{
            absDenum = -denum;
            signDenum = -1;
        }
        long min = absNumer < absDenum ? absNumer : absDenum;
        while(i <= min){
            if (absNumer % i == 0 && absDenum % i == 0){
                absNumer /= i;
                absDenum /= i;
                min /= i;
            }else{
                ++i;
            }
        }
        numer = signNumer * absNumer;
        denum = signDenum * absDenum;
    }
    public String toString(){
        return "" + numer + "/" + denum;
    }

    public boolean equals(Rational r){
        return numer == r.numer && denum == r.denum;
    }
    public boolean equals(long e){
        return numer == e && denum == 1;
    }

    public Rational plus(Rational r){
        long newNumer = numer * r.denum + denum * r.numer;
        long newDenum = denum * r.denum;
        Rational rez = new Rational(newNumer, newDenum);
        rez.simplify();
        return rez;
    }
    public Rational plus(long i){
        return this.plus(new Rational(i));
    }
    public Rational changeSign(){
        return new Rational(-numer, denum);
    }
    public Rational minus(Rational r){
        return this.plus(r.changeSign());
    }
    public Rational minus(long i){
        return this.plus(new Rational(-i));
    }
    public Rational multiply(Rational r){
        long newNumer = numer * r.numer;
        long newDenum = denum * r.denum;
        Rational rez = new Rational(newNumer, newDenum);
        rez.simplify();
        return rez;
    }
    public Rational multiply(long i){
        return this.multiply(new Rational(i));
    }
    public Rational divide(Rational r){
        long newNumer = numer * r.denum;
        long newDenum = denum * r.numer;
        if(newDenum == 0){
            throw new ArithmeticException();
        }
        Rational rez = new Rational(newNumer, newDenum);
        rez.simplify();
        return rez;
    }
    public Rational divide(long i){
        return this.divide(new Rational(i));
    }
    public double doubleValue(){
        return (double)numer/(double)denum;
    }

    public boolean isZero(){
        return getNumer() == 0;
    }

    public static long[] makeLongMas(Rational[] masRat){
        int length = masRat.length;
        long[] masLong = new long[length];
        long[] masSignLong = new long[length];
        long fullDenum = 1;
        for (Rational r: masRat)
            fullDenum *= r.getDenum();

        for(int i = 0; i < length; ++i) {
            masLong[i] = fullDenum * masRat[i].getNumer() / masRat[i].getDenum();
            if (masLong[i] > 0) {
                masSignLong[i] = 1;
            }else{
                masLong[i] = -masLong[i];
                masSignLong[i] = -1;
            }
        }

        long min = masLong[0];
        for (long j: masLong) {
            if(min < j)
                min = j;
        }

        int i = 2;
        while (i <= min ){
            boolean isDividing = true;
            for(long e : masLong)
                isDividing &= (e%i == 0);
            if(isDividing){
                for(int j = 0; j < length; ++j)
                    masLong[j] /= i;
                min /= i;
            }else{
                ++i;
            }
        }

        for(i = 0; i < length; ++i){
            masLong[i] *= masSignLong[i];
        }

        return masLong;
    }

}
