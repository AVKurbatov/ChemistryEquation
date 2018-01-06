package avkurbatov_home.chemistry_equation;

class Messages {
    private Messages(){}

    static{
        _lang = 0;
        _tip = 0;
        _forUser = 0;
    }

    private static int _lang;/*0 --- English,
                       1 --- Russian*/
    private static int _tip;
    private static int _forUser;

    static void setLang(int lang){
        _lang = lang;
    }

    private static String getMessage(String[] mas){
        if ( (_lang == 0 || _lang == 1) || mas.length < (_lang + 1) ){
            return mas[_lang];
        }
        return mas[0];
    }

    final static String MESSAGE_quit = "quit";

    private final static String[] MESSAGE_tip_0 = {"This program will automatically solve coefficients of your chemistry equation.\n" +
            "Type the equation like \"Na + H.Cl -> Na.Cl + H_2\".",
            "Программа автоматически рассчитывает коэффициенты введённого уравнения реакции.\n" +
                    "Введите уравнение типа \"Na + H.Cl -> Na.Cl + H_2\"."
    };

    private final static String[] MESSAGE_сalculate_coefficients = { "Calculate coefficients",
            "Рассчитать коэффициенты"};
    static String getMESSAGE_сalculate_coefficients(){
        return getMessage(MESSAGE_сalculate_coefficients);    }

    private final static String[] MESSAGE_tip_1 = {"Try something more complex, like \"na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o\".",
            "Попробуйте что-нибудь более сложное, например, \"na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o\"."};
    private final static String[] MESSAGE_tip_2 = {"The program can find several solutions, try something like \"c + o_2 -> c.o + c.o_2\".",
            "Если уравнение содержит несколько решений, программа их найдёт. Попробуйте ввести, например, \"c + o_2 -> c.o + c.o_2\"."};
    private final static String[] MESSAGE_tip_3 = {"Also, the program can find some errors in equations, try \"c + o_2 + c.o_2 -> c.o\".",
                        "Также программа может находить ошибки в уравнениях, попробуйте \"c + o_2 + c.o_2 -> c.o\"."};
    private final static String[] MESSAGE_tip_4 = {"The program does not check if the reaction is possible.",
                                           "Программа не проверяет, возможна ли реакция"};

    private static String getMESSAGE_tip_0(){
        return getMessage(MESSAGE_tip_0);    }
    private static String getMESSAGE_tip_1(){
        return getMessage(MESSAGE_tip_1);    }
    private static String getMESSAGE_tip_2(){
        return getMessage(MESSAGE_tip_2);    }
    private static String getMESSAGE_tip_3(){
        return getMessage(MESSAGE_tip_3);    }
    private static String getMESSAGE_tip_4(){
        return getMessage(MESSAGE_tip_4);    }

    static String getMessageNextTip(){
        String rez = getMessageSameTip();
        _tip = (_tip + 1) % 4;
        return rez;
    }
    static String getMessageSameTip(){
        String rez;
        switch (_tip) {
            case 0:
                rez = getMESSAGE_tip_0();
                break;
            case 1:
                rez = getMESSAGE_tip_1();
                break;
            case 2:
                rez = getMESSAGE_tip_2();
                break;
            case 3:
                rez = getMESSAGE_tip_3();
                break;
            case 4:
                rez = getMESSAGE_tip_4();
                break;
            default:
                rez = getMESSAGE_tip_0();
                break;
        }
        return rez;
    }

    private final static String[] MESSAGE_tip_answer_language = {
            "Switch language and tap button \"" + MESSAGE_сalculate_coefficients[0] + "\" to receive " +
                    "answer on another language.",
            "Для получения ответа на другом языке " +
                    "переключите язык и снова нажмите на кнопку \"" + MESSAGE_сalculate_coefficients[1] + "\"."};

    static String getMessage_tip_answer_language(){
        return getMessage(MESSAGE_tip_answer_language);    }

    private final static String[] MESSAGE_for_user_0 = MESSAGE_tip_0;
    private final static String[] MESSAGE_for_user_1 = {"To close the program type \"" + MESSAGE_quit + "\".",
             "Чтобы закрыть программу, введите \"" + MESSAGE_quit + "\"."};
    private final static String[] MESSAGE_for_user_2 = MESSAGE_tip_1;
    private final static String[] MESSAGE_for_user_3 = MESSAGE_tip_2;
    private final static String[] MESSAGE_for_user_4 = MESSAGE_tip_3;
    private final static String[] MESSAGE_for_user_continue = {"Try more or print \"" + MESSAGE_quit + "\".",
            "Попробуйте ещё или введите \"" + MESSAGE_quit + "\"."};
    private static String getMESSAGE_for_user_0(){
        return getMessage(MESSAGE_for_user_0);    }
    private static String getMESSAGE_for_user_1(){
        return getMessage(MESSAGE_for_user_1);    }
    private static String getMESSAGE_for_user_2(){
        return getMessage(MESSAGE_for_user_2);    }
    private static String getMESSAGE_for_user_3(){
        return getMessage(MESSAGE_for_user_3);    }
    private static String getMESSAGE_for_user_4(){
        return getMessage(MESSAGE_for_user_4);    }
    private static String getMESSAGE_for_user_continue(){
        return getMessage(MESSAGE_for_user_continue);    }

    static String getMessageForUser(){
        String rez;
        switch (_forUser) {
            case 0:
                rez = getMESSAGE_for_user_0();
                break;
            case 1:
                rez = getMESSAGE_for_user_1();
                break;
            case 2:
                rez = getMESSAGE_for_user_2();
                break;
            case 3:
                rez = getMESSAGE_for_user_3();
                break;
            case 4:
                rez = getMESSAGE_for_user_4();
                break;
            default:
                rez = getMESSAGE_for_user_continue();
        }
        if(_forUser < 5)
            ++_forUser;
        return rez;
    }

    private final static String[] MESSAGE_critical_error = {"Critical error, please, contact the developer.",
    "Критическая ошибка, пожалуйста, обратитесь к разработчику"};
    static String getMESSAGE_critical_error(){
        return getMessage(MESSAGE_critical_error);    }

    private final static String[] MESSAGE_goodbye = {"Goodbye!", "До свидания!"};
    static String getMESSAGE_goodbye(){
        return getMessage(MESSAGE_goodbye);    }

    private final static String[] MESSAGE_in_out_error = {"In-out error.\n", "Ошибка ввода-вывода.\n"};
    static String getMESSAGE_in_out_error(){
        return getMessage(MESSAGE_in_out_error);    }

    final static String MESSAGE_CULTIVATOR = "->";

    private final static String[] MESSAGE_error_missed_cultivator = {"Error! Cultivator \"%s\" missed in the equation!",
            "Ошибка! В уравнении отсутствует разделитель \"%s\"!"};
    static String getMESSAGE_error_missed_cultivator(){
        return getMessage(MESSAGE_error_missed_cultivator);    }

    private final static String[] MESSAGE_error_substance_twice_in_left = {"Error! A substance with a name \"%s\" occurs twice in the left part of the equation!",
            "Ошибка! Вещество с названием \"%s\" дважды присутствует в левой части уравнения!"};
    static String getMESSAGE_error_substance_twice_in_left(){
        return getMessage(MESSAGE_error_substance_twice_in_left);    }

    private final static String[] MESSAGE_error_substance_twice_in_right = {"Error! A substance with a name \"%s\" occurs twice in the right part of the equation!",
            "Ошибка! Вещество с названием \"%s\" дважды присутствует в правой части уравнения!"};
    static String getMESSAGE_error_substance_twice_in_right(){
        return getMessage(MESSAGE_error_substance_twice_in_right);    }

    private final static String[] MESSAGE_error_substance_twice_in_both = {"Error! A substance with a name \"%s\" occurs in both parts of the equation!",
            "Ошибка! Вещество с названием \"%s\" присутствует в обеих частях уравнения!"};
    static String getMESSAGE_error_substance_twice_in_both(){
        return getMessage(MESSAGE_error_substance_twice_in_both);    }

    private final static String[] MESSAGE_no_solutions_of_the_equation = {"There are no solutions of the equation. Check equation.",
            "Решений нет. Проверьте правильность задания уравнения."};
    static String getMESSAGE_no_solutions_of_the_equation(){
        return getMessage(MESSAGE_no_solutions_of_the_equation);    }

    private final static String[] MESSAGE_the_only_solution_found = {"The only solution found:\n",
            "Единственное найденное решение:\n"};
    static String getMESSAGE_the_only_solution_found(){
        return getMessage(MESSAGE_the_only_solution_found);    }

    private final static String[] MESSAGE_common_solution_is_a_linear_combination = {"Common solution is a linear combination of the following particular solutions:\n",
            "Общее решение является линейной комбинацией следующих частных решений:\n"};
    static String getMESSAGE_common_solution_is_a_linear_combination(){
        return getMessage(MESSAGE_common_solution_is_a_linear_combination);    }

    private final static String[] MESSAGE_the_solution_may_have_been_written_incorrectly = {"The solution may have been written incorrectly\n",
            "Возможно, уравнение было записано неправильно.\n"};
    static String getMESSAGE_the_solution_may_have_been_written_incorrectly (){
        return getMessage(MESSAGE_the_solution_may_have_been_written_incorrectly );    }

    private final static String[] MESSAGE_version = {"Version ", "Вариант "};
    static String getMESSAGE_version(){
        return getMessage(MESSAGE_version);    }

    private final static String[] MESSAGE_to_obtain_additional_solutions_move_from_the_left_to_the_right = {"To obtain additional solutions, move from the left part of the equation to the right ",
            "Для получения дополнительных решений следует перенести из левой части в правую "};
    static String getMESSAGE_to_obtain_additional_solutions_move_from_the_left_to_the_right(){
        return getMessage(MESSAGE_to_obtain_additional_solutions_move_from_the_left_to_the_right);    }

    private final static String[] MESSAGE_substance = {"substance ", "вещество "};
    static String getMESSAGE_substance(){
        return getMessage(MESSAGE_substance);    }

    private final static String[] MESSAGE_substances = {"substances ", "вещества "};
    static String getMESSAGE_substances(){
        return getMessage(MESSAGE_substances);    }

    private final static String[] MESSAGE_also_move_from_the_right_ti_the_left = {"Also, move from the right to the left ",
            "Также следует перенести из правой части в левую "};
    static String getMESSAGE_also_move_from_the_right_ti_the_left(){
        return getMessage(MESSAGE_also_move_from_the_right_ti_the_left);    }

    private final static String[] MESSAGE_to_obtain_additional_solutions_move_from_the_right_to_the_left = {"To obtain additional solutions, move from the right part of the equation to the left ",
            "Для получения дополнительных решений следует перенести из правой части в левую "};
    static String getMESSAGE_to_obtain_additional_solutions_move_from_the_right_to_the_left(){
        return getMessage(MESSAGE_to_obtain_additional_solutions_move_from_the_right_to_the_left);    }

    private final static String[] MESSAGE_error_the_word_is_bad_for_a_name_of_substance = {"Error! The word \"%s\" is bad for a name of a substance!",
            "Ошибка! Слово \"%s\" плохо подходит для названия вещества!"};
    static String getMESSAGE_error_the_word_is_bad_for_a_name_of_substance(){
        return getMessage(MESSAGE_error_the_word_is_bad_for_a_name_of_substance);    }

    private final static String[] MESSAGE_error = {"Error!\n", "Ошибка!\n"};
    static String getMESSAGE_error(){
        return getMessage(MESSAGE_error);    }

    private final static String[] MESSAGE_Element = {"Element ", "Элемент "};
    static String getMESSAGE_Element(){
        return getMessage(MESSAGE_Element);    }

    private final static String[] MESSAGE_Elements = {"Elements ", "Элементы "};
    static String getMESSAGE_Elements(){
        return getMessage(MESSAGE_Elements);    }

    private final static String[] MESSAGE_is_present_in_the_left_part_of_the_equation_but_it_is_not_present_in_the_right_part_of_the_equation =
            {"is present in the left part of the equation, but it is not present in the right part of the equation.\n",
                    "присутствует в левой части уравнения, но отсутствует в правой части уравнения"};
    static String getMESSAGE_is_present_in_the_left_part_of_the_equation_but_it_is_not_present_in_the_right_part_of_the_equation(){
        return getMessage(MESSAGE_is_present_in_the_left_part_of_the_equation_but_it_is_not_present_in_the_right_part_of_the_equation);    }

    private final static String[] MESSAGE_are_present_in_the_left_part_of_the_equation_but_they_are_not_present_in_the_right_part_of_the_equation =
            {"are present in the left part of the equation, but they are not present in the right part of the equation.\n",
                    "присутствуют в левой части уравнения, но отсутствуют в правой части уравнения"};
    static String getMESSAGE_are_present_in_the_left_part_of_the_equation_but_they_are_not_present_in_the_right_part_of_the_equation(){
        return getMessage(MESSAGE_are_present_in_the_left_part_of_the_equation_but_they_are_not_present_in_the_right_part_of_the_equation);    }

    private final static String[] MESSAGE_is_present_in_the_right_part_of_the_equation_but_it_is_not_present_in_the_left_part_of_the_equation =
            {"is present in the right part of the equation, but it is not present in the left part of the equation.\n",
                    "присутствует в правой части уравнения, но отсутствует в левой части уравнения"};
    static String getMESSAGE_is_present_in_the_right_part_of_the_equation_but_it_is_not_present_in_the_left_part_of_the_equation(){
        return getMessage(MESSAGE_is_present_in_the_right_part_of_the_equation_but_it_is_not_present_in_the_left_part_of_the_equation );    }

    private final static String[] MESSAGE_are_present_in_the_right_part_of_the_equation_but_they_are_not_present_in_the_left_part_of_the_equation =
            {"are present in the right part of the equation, but they are not present in the left part of the equation.\n",
                    "присутствуют в правой части уравнения, но отсутствуют в левой части уравнения"};
    static String getMESSAGE_are_present_in_the_right_part_of_the_equation_but_they_are_not_present_in_the_left_part_of_the_equation(){
        return getMessage(MESSAGE_are_present_in_the_right_part_of_the_equation_but_they_are_not_present_in_the_left_part_of_the_equation);    }

    private final static String[] MESSAGE_error_unable_to_get_a_name_of_the_element = {"Error! Unable to get a name of the element!",
            "Ошибка! Невозможно разобрать название элемента!"};
    static String getMESSAGE_error_unable_to_get_a_name_of_the_element(){
        return getMessage(MESSAGE_error_unable_to_get_a_name_of_the_element);    }

    private final static String[] MESSAGE_error_element_does_not_exist = {"Error! The element \"%s\" does not exist!",
            "Ошибка! Элемент \"%s\" не существует!"};
    static String getMESSAGE_error_element_does_not_exist(){
        return getMessage(MESSAGE_error_element_does_not_exist);    }

    private final static String[] MESSAGE_error_class_missing = {"A class \"%s\" missed in a local library. Put a jar-file with this class into a subdirectory /lib in the actual directory",
            "В локальной библиотеке отсутствует класс \"%s\". Разместите jar-файл с отсутсвующим классом в подкаталог /lib/ в каталоге с настоящей программой."};
    static String getMESSAGE_error_class_missing(){
        return getMessage(MESSAGE_error_class_missing);    }

    private final static String[] MESSAGE_error_in_equation = {"Error in equation",
            "Ошибка в уравнении"};
    static String getMESSAGE_error_in_equation(){
        return getMessage(MESSAGE_error_in_equation);    }

    private final static String[] MESSAGE_сritical_error = {"Critical error",
            "Критическая ошибка"};
    static String getMESSAGE_сritical_error(){
        return getMessage(MESSAGE_сritical_error);    }

    private final static String[] MESSAGE_chemical_equation_solver = {"Chemical equation solver",
            "Решение уравнений реакции"};
    static String getMESSAGE_chemical_equation_solver(){
        return getMessage(MESSAGE_chemical_equation_solver);    }

    private final static String[] MESSAGE_type_chemical_equation_here = {"Type chemical equation here:",
            "Введите уравнение реакции"};
    static String getMESSAGE_type_chemical_equation_here(){
        return getMessage(MESSAGE_type_chemical_equation_here);    }

    private final static String[] MESSAGE_tip = { "Tip: ",
            "Совет: "};
    static String getMESSAGE_tip(){
        return getMessage(MESSAGE_tip);    }

    private final static String[] MESSAGE_next_tip = { "Next tip",
            "Далее"};
    static String geMESSAGE_next_tip(){
        return getMessage(MESSAGE_next_tip);    }

    private final static String[] MESSAGE_choose_language = { "Choose language",
            "Выберите язык"};
    static String geMESSAGE_choose_language(){
        return getMessage(MESSAGE_choose_language);    }

}


























