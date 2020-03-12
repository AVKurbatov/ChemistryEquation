package avkurbatov_home.chemistry_equation.ui;

import avkurbatov_home.chemistry_equation.math.EquationSolver;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static avkurbatov_home.chemistry_equation.constants.Constants.QUIT;
import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

/**
 * Alternative enter point to run solver
 */
public class ConsoleVersion {

    public static void main(String[] args){
        EquationSolver equationSolver = new EquationSolver();

        try(BufferedReader br = new BufferedReader( new InputStreamReader(System.in) )){
            String userMessage;
            boolean shouldContinue = true;
            do {
                log(MESSENGER.getMessageForUser());
                userMessage = br.readLine();

                shouldContinue = QUIT.equalsIgnoreCase(userMessage);

                if (shouldContinue) {
                    String message;
                    try {
                        message = equationSolver.solveChemistryEquation(userMessage);
                    } catch (ParsingEquationException e) {
                        message = e.toString();
                    } catch (Exception e) {
                        message = MESSENGER.criticalError();
                    }
                    log(message);
                }
            } while(shouldContinue);
            log(MESSENGER.goodbye());
        } catch(IOException e) {
            log( MESSENGER.inOutError() );
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
