package chemistry_equation;

import java.io.IOException;

/**
 * Created by Александр on 23.09.2017.
 */
public class ParsingEquationException extends IOException {
    private String message;
    ParsingEquationException(String message){
        this.message = message;
    }
    public String toString(){
        return message;
    }
}
