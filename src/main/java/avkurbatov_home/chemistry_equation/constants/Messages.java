package avkurbatov_home.chemistry_equation.constants;

import static avkurbatov_home.chemistry_equation.constants.Constants.QUIT;

public interface Messages {

    String[] CALCULATE_COEFFICIENTS = { "Calculate coefficients",
            "Рассчитать коэффициенты"};

    String[][] TIPS = {
            {"Try something simple like \"h_2 + o_2 -> h_2.o\".",
                    "Попробуйте ввести что-нибудь простое, например, \"h_2 + o_2 -> h_2.o\"."},
            {"The program can find several solutions, try something like \"c + o_2 -> c.o + c.o_2\".",
                    "Если уравнение содержит несколько решений, программа их найдёт. Попробуйте ввести, например, \"c + o_2 -> c.o + c.o_2\"."},
            {"Try something more complex, like \"na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o\".",
                    "Попробуйте что-нибудь более сложное, например, \"na.o.h + h_2.s.o_4 -> na_2.s.o_4 + H_2.o\"."},
            {"Also, the program can find some errors in equations, try \"c + o_2 + c.o_2 -> c.o\".",
                    "Также программа может находить ошибки в уравнениях, попробуйте \"c + o_2 + c.o_2 -> c.o\"."},
            {"The program does not check if the reaction is possible.",
                    "Программа не проверяет, возможна ли реакция"}
    };

    String[] TIP_ANSWER_LANGUAGE = {
            "Switch language and tap button \"" + CALCULATE_COEFFICIENTS[0] + "\" to receive " +
                    "answer on another language.",
            "Для получения ответа на другом языке " +
                    "переключите язык и снова нажмите на кнопку \"" + CALCULATE_COEFFICIENTS[1] + "\"."};

    String[][] MESSAGES_FOR_USER = {TIPS[0],
            {"To close the program type \"" + QUIT + "\".",
                    "Чтобы закрыть программу, введите \"" + QUIT + "\"."},
            TIPS[1],
            TIPS[2],
            TIPS[3],
            {"Try more or print \"" + QUIT + "\".",
                    "Попробуйте ещё или введите \"" + QUIT + "\"."}
    };
    String[] CRITICAL_ERROR = {"Critical error, please, contact the developer.",
            "Критическая ошибка, пожалуйста, обратитесь к разработчику"};
    String[] GOODBYE = {"Goodbye!", "До свидания!"};
    String[] IN_OUT_ERROR = {"In-out error.\n", "Ошибка ввода-вывода.\n"};
    String[] ERROR_MISSED_DELIMITER = {"Error! Delimiter \"%s\" missed in the equation!",
            "Ошибка! В уравнении отсутствует разделитель \"%s\"!"};
    String[] ERROR_SUBSTANCE_TWICE_IN_LEFT = {"Error! A substance with a name \"%s\" occurs twice in the left part of the equation!",
            "Ошибка! Вещество с названием \"%s\" дважды присутствует в левой части уравнения!"};
    String[] ERROR_SUBSTANCE_TWICE_IN_RIGHT = {"Error! A substance with a name \"%s\" occurs twice in the right part of the equation!",
            "Ошибка! Вещество с названием \"%s\" дважды присутствует в правой части уравнения!"};
    String[] ERROR_SUBSTANCE_TWICE_IN_BOTH = {"Error! Substances with names \"%s\" occur in both parts of the equation!",
            "Ошибка! Вещества с названиями \"%s\" присутствует в обеих частях уравнения!"};
    String[] NO_SOLUTIONS_OF_THE_EQUATION = {"There are no solutions of the equation. Check equation.",
            "Решений нет. Проверьте правильность задания уравнения."};
    String[] THE_ONLY_SOLUTION_FOUND = {"The only solution found:\n",
            "Единственное найденное решение:\n"};
    String[] COMMON_SOLUTION_IS_A_LINEAR_COMBINATION = {"Common solution is a linear combination of the following particular solutions:\n",
            "Общее решение является линейной комбинацией следующих частных решений:\n"};
    String[] MAYBE_THE_EQUATION_WAS_SET_INCORRECTLY = {"Maybe the equation was set incorrectly\n",
            "Возможно, уравнение было записано неправильно.\n"};
    String[] VERSION = {"Version ", "Вариант "};
    String[] TO_OBTAIN_ADDITIONAL_SOLUTIONS_MOVE_FROM_THE_LEFT_TO_THE_RIGHT = {"To obtain additional solutions, move from the left part of the equation to the right ",
            "Для получения дополнительных решений следует перенести из левой части в правую "};
    String[] SUBSTANCE = {"substance ", "вещество "};
    String[] SUBSTANCES = {"substances ", "вещества "};
    String[] ALSO_MOVE_FROM_THE_RIGHT_TO_THE_LEFT = {"Also, move from the right to the left ",
            "Также следует перенести из правой части в левую "};
    String[] TO_OBTAIN_ADDITIONAL_SOLUTIONS_MOVE_FROM_THE_RIGHT_TO_THE_LEFT = {"To obtain additional solutions, move from the right part of the equation to the left ",
            "Для получения дополнительных решений следует перенести из правой части в левую "};
    String[] ERROR_THE_WORD_IS_BAD_FOR_A_NAME_OF_SUBSTANCE = {"Error! The word \"%s\" is bad for a name of a substance!",
            "Ошибка! Слово \"%s\" плохо подходит для названия вещества!"};
    String[] ERROR = {"Error!\n", "Ошибка!\n"};
    String[] ELEMENT = {"Element ", "Элемент "};
    String[] ELEMENTS = {"Elements ", "Элементы "};
    String[] IS_PRESENT_ONLY_IN_LEFT_PART =
            {"is present in the left part of the equation, but it is not present in the right part of the equation.\n",
                    "присутствует в левой части уравнения, но отсутствует в правой части уравнения"};
    String[] ARE_PRESENT_ONLY_IN_LEFT_PART =
            {"are present in the left part of the equation, but they are not present in the right part of the equation.\n",
                    "присутствуют в левой части уравнения, но отсутствуют в правой части уравнения"};
    String[] IS_PRESENT_ONLY_IN_RIGHT_PART =
            {"is present in the right part of the equation, but it is not present in the left part of the equation.\n",
                    "присутствует в правой части уравнения, но отсутствует в левой части уравнения"};
    String[] ARE_PRESENT_ONLY_IN_RIGHT_PART =
            {"are present in the right part of the equation, but they are not present in the left part of the equation.\n",
                    "присутствуют в правой части уравнения, но отсутствуют в левой части уравнения"};
    String[] ERROR_UNABLE_TO_GET_ELEMENT_NAME = {"Error! Unable to get element name!",
            "Ошибка! Невозможно разобрать название элемента!"};
    String[] ERROR_ELEMENT_DOES_NOT_EXIST = {"Error! The element \"%s\" does not exist!",
            "Ошибка! Элемент \"%s\" не существует!"};
    String[] ERROR_IN_EQUATION = {"Error in equation", "Ошибка в уравнении"};
    String[] CHEMICAL_EQUATION_SOLVER_TITLE = {"Chemical equation solver", "Решение уравнений реакции"};
    String[] TYPE_CHEMICAL_EQUATION_HERE = {"Type chemical equation here:", "Введите уравнение реакции"};
    String[] TIP = { "Tip: ", "Совет: "};
    String[] NEXT_TIP = { "Next tip", "Далее"};
    String[] CHOOSE_LANGUAGE = { "Choose language", "Выберите язык"};
    String[] INDEX_MUST_BE_POSITIVE = {"Index must be positive but it equals \"%d\"",
            "Индекс должен быть положительным, но он равен \"%d\""};
    String[] INDEX_MUST_BE_POSITIVE_NUMBER = {"Index must be positive number but it equals \"%s\"",
                    "Индекс должен быть положительным числом, но он равен \"%s\""};
}
