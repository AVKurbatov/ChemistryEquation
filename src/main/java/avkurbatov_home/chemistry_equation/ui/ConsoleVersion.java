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
                System.out.println(MESSENGER.getMessageForUser());
                userMessage = br.readLine();

                shouldContinue = !userMessage.trim().toLowerCase().equals(QUIT);

                if (shouldContinue) {
                    String message;
                    try {
                        message = equationSolver.solveChemistryEquation(userMessage);
                    } catch (ParsingEquationException e) {
                        message = e.toString();
                    } catch (Exception e) {
                        message = MESSENGER.criticalError();
                    }
                    System.out.println(message);
                }
            } while(shouldContinue);
            System.out.println(MESSENGER.goodbye());
        } catch(IOException e) {
            System.out.println( MESSENGER.inOutError() );
            e.printStackTrace();
        }

    }
}
