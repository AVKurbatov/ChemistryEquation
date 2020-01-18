package avkurbatov_home.chemistry_equation.exceptions;

import java.io.IOException;

/**
 * This Exception is thrown then the application has some problems with equation parsing
 * */
public class ParsingEquationException extends IOException {
    public ParsingEquationException(String message) {
        super(message);
    }
    public ParsingEquationException(String message, Exception e) {
        super(message, e);
    }

    @Override
    public String toString() {
        return getLocalizedMessage();
    }
}
