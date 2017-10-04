package chemistry_equation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Александр on 01.10.2017.
 */
public class ConsoleVersionChemicalEquationSolver implements ChemistryEquationMessages {
    private static final String[] MESSAGES = {MESSAGE_for_user_0,
            MESSAGE_for_user_1,
            MESSAGE_for_user_2,
            MESSAGE_for_user_3};

    private static final int[] POSITIONS_OF_MESSAGES = {0, 1, 2, 3};

    public static void main(String[] args){
        try(BufferedReader br = new BufferedReader( new InputStreamReader(System.in) )){
            int callsCounter = 0;
            int posCounter = 0;
            String userMessage;
            boolean isContinued = true;
            do {
                if(POSITIONS_OF_MESSAGES[posCounter] == callsCounter){
                    if(MESSAGES.length > posCounter)
                        System.out.println(MESSAGES[posCounter]);
                    ++posCounter;
                }else{
                    System.out.println( MESSAGE_for_user_continue );
                }
                userMessage = br.readLine();

                isContinued = !userMessage.trim().toLowerCase().equals(MESSAGE_quit);

                if(isContinued)
                    try {
                        String message = ChemicalEquationSolverMathPart.solveChemistryEquation(userMessage);
                        System.out.println(message);
                        ++callsCounter;
                    }catch (ParsingEquationException e){
                        System.out.println(e.toString());
                    }catch (ElementsAndSubstancesException | ArrayIndexOutOfBoundsException e){
                        System.out.println(MESSAGE_critical_error);
                    }
                else
                    System.out.println(MESSAGE_goodbye);
            }while(isContinued);
        }catch(IOException e){
            System.out.println(MESSAGE_in_out_error);
            e.printStackTrace();
        }

    }
}
