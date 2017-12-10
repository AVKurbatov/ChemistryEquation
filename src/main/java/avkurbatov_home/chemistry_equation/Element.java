package avkurbatov_home.chemistry_equation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Александр on 28.09.2017.
 */
public class Element implements Comparable<Element>, MendeleevTable{
    final private String _name;
    private static TreeSet<String> ELEMENT_NAME_SET;
    static{
        ELEMENT_NAME_SET = new TreeSet<>();
        ELEMENT_NAME_SET.addAll(Arrays.asList(ELEMENT_NAMES));
    }

    Element(String name) throws ParsingEquationException{
        String nameTmp = name.trim();
        if(nameTmp.length() == 0)
            throw new ParsingEquationException(Messages.getMESSAGE_error_unable_to_get_a_name_of_the_element());
        nameTmp = nameTmp.substring(0, 1).toUpperCase() + nameTmp.substring(1, nameTmp.length()).toLowerCase();

        if(!ELEMENT_NAME_SET.contains(nameTmp))
            throw new ParsingEquationException(String.format(Messages.getMESSAGE_error_element_does_not_exist(), nameTmp));

        _name = nameTmp;
    }
    public int compareTo(Element e){
        return _name.compareTo(e._name);
    }
    public String getName() {
        return _name;
    }
    public String toString() {
        return _name;
    }
}
