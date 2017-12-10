package avkurbatov_home.chemistry_equation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Александр on 01.10.2017.
 */
public class ConsoleVersionChemicalEquationSolver {

    public static void main(String[] args){
        try(BufferedReader br = new BufferedReader( new InputStreamReader(System.in) )){
            String userMessage;
            boolean isContinued = true;
            do {
                System.out.println(Messages.getMessageForUser());
                userMessage = br.readLine();

                isContinued = !userMessage.trim().toLowerCase().equals( Messages.MESSAGE_quit );

                if(isContinued)
                    try {
                        String message = ChemicalEquationSolverMathPart.solveChemistryEquation(userMessage);
                        System.out.println(message);
                    }catch (ParsingEquationException e){
                        System.out.println(e.toString());
                    }catch (ElementsAndSubstancesException | ArrayIndexOutOfBoundsException e){
                        System.out.println( Messages.getMESSAGE_critical_error() );
                    }
                else
                    System.out.println( Messages.getMESSAGE_goodbye() );
            }while(isContinued);
        }catch(IOException e){
            System.out.println( Messages.getMESSAGE_in_out_error() );
            e.printStackTrace();
        }

    }
}
