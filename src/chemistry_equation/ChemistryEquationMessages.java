package chemistry_equation;

/**
 * Created by Александр on 01.10.2017.
 */
public interface ChemistryEquationMessages {
    String MESSAGE_quit = "quit";

    String MESSAGE_for_user_0 = "This program will automatically solve coefficients of your chemistry equation.\n" +
            "Type the equation like \"Na + H.Cl -> Na.Cl + H_2\".\n" +
            "To close the program type \"" + MESSAGE_quit + "\".";
    String MESSAGE_for_user_1 = "Try something more complex, like \"na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o\".";
    String MESSAGE_for_user_2 = "The program can find several solutions, try something like \"c + o_2 -> c.o + c.o_2\".";
    String MESSAGE_for_user_3 = "Also, the program can find some errors in equations, try \"c + o_2 + c.o_2 -> c.o\".";
    String MESSAGE_for_user_continue = "Try more or print \"" + MESSAGE_quit + "\".";
    String MESSAGE_critical_error = "Critical error, please, contact the developer.";
    String MESSAGE_goodbye = "Goodbye!";
    String MESSAGE_in_out_error = "In-out error.\n";
    String MESSAGE_CULTIVATOR = "->";
    String MESSAGE_error_missed_cultivator = "Error! Cultivator \"%s\" missed in the equation!";
    String MESSAGE_error_substance_twice_in_left = "Error! A substance with a name \"%s\" occurs twice in the left part of the equation!";
    String MESSAGE_error_substance_twice_in_right = "Error! A substance with a name \"%s\" occurs twice in the right part of the equation!";
    String MESSAGE_error_substance_twice_in_both = "Error! A substance with a name \"%s\" occurs in both parts of the equation!";
    String MESSAGE_no_solutions_of_the_equation = "There are no solutions of the equation. Check equation.";//"Решений нет. Проверьте правильность задания уравнения.";
    String MESSAGE_the_only_solution_found = "The only solution found:\n";// "Единственное найденное решение:\n";
    String MESSAGE_common_solution_is_a_linear_combination = "Common solution is a linear combination of the following particular solutions:\n";// "Общее решение является линейной комбинацией следующих частных решений:\n";
    String MESSAGE_the_solution_may_have_been_written_incorrectly = "The solution may have been written incorrectly\n"; //""Возможно, уравнение было записано неправильно.\n";
    String MESSAGE_version = "Version ";//"Вариант ";
    String MESSAGE_to_obtain_additional_solutions_move_from_the_left_to_the_right = "To obtain additional solutions, move from the left part of the equation to the right ";
    // "Для получения дополнительных решений следует перенести из левой части в правую ";
    String MESSAGE_substance = "substance ";//"вещество ";
    String MESSAGE_substances = "substances "; //"вещества ";
    String MESSAGE_also_move_from_the_right_ti_the_left = "Also, move from the right to the left ";//"Также следует перенести из правой части в левую ";
    String MESSAGE_to_obtain_additional_solutions_move_from_the_right_to_the_leftt = "To obtain additional solutions, move from the right part of the equation to the left ";
            //"Для получения дополнительных решений следует перенести из правой части в левую ";
    String MESSAGE_error_the_word_is_bad_for_a_name_of_substance = "Error! The word \"%s\" is bad for a name of a substance!";
    String MESSAGE_error = "Error!\n";
    String MESSAGE_Element = "Element ";
    String MESSAGE_Elements = "Elements ";
    String MESSAGE_is_present_in_the_left_part_of_the_equation_but_it_is_not_present_in_the_right_part_of_the_equation = "is present in the left part of the equation, but it is not present in the right part of the equation.\n";
    String MESSAGE_are_present_in_the_left_part_of_the_equation_but_they_are_not_present_in_the_right_part_of_the_equation = "are present in the left part of the equation, but they are not present in the right part of the equation.\n";
    String MESSAGE_is_present_in_the_right_part_of_the_equation_but_it_is_not_present_in_the_left_part_of_the_equation = "is present in the right part of the equation, but it is not present in the left part of the equation.\n";
    String MESSAGE_are_present_in_the_right_part_of_the_equation_but_they_are_not_present_in_the_left_part_of_the_equation = "are present in the right part of the equation, but they are not present in the left part of the equation.\n";
}

