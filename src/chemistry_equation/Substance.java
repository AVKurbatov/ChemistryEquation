package chemistry_equation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Александр on 28.09.2017.
 */
public class Substance implements Comparable<Substance>, ChemistryPatterns{
    private String name;
    private TreeMap<Element, Integer> elements;
    final private static Pattern regex = Pattern.compile(ELEMENT_PATTERN);

    public int compareTo(Substance s){
        Map<Element, Integer> sElements = s.getElementsWithIndexes();
        boolean isEqual = false;
        if( elements.size() == sElements.size() ){
            isEqual = true;
            for( Element element : elements.keySet() ){
                if(!sElements.containsKey(element)){
                    isEqual = false;
                    break;
                }else{
                    if(!elements.get(element).equals(sElements.get(element))){
                        isEqual = false;
                        break;
                    }
                }
            }
        }
        if(isEqual)
            return 0;
        else
            return getName().compareTo(s.getName());
    }

//    public static void main(String[] args) throws ElementsAndSubstancesException {
//
//        Substance s1 = new Substance("cl.h_2");
//        Substance s2 = new Substance("h_2.cl.l");
//
//        System.out.println(s1.compareTo(s2));
//    }

    Substance(String str) throws ParsingEquationException{
        elements = new TreeMap<>();
        StringBuilder nameBuilder = new StringBuilder("");

        Matcher regexMatcher = regex.matcher(str);
        boolean isFirst = true;
        while (regexMatcher.find()) {
            if(isFirst)
                isFirst = false;
            else
                nameBuilder.append(".");

            String fullElement = regexMatcher.group();
            int posOfSeparator = fullElement.indexOf("_");
            int index = 0;
            if (posOfSeparator == -1) {
                posOfSeparator = fullElement.length();
                index = 1;
            }else{
                index = Integer.parseUnsignedInt(fullElement.substring(posOfSeparator + 1));
            }
            String elementName = fullElement.substring(0, posOfSeparator);

            Element element = new Element(elementName);

            if(elements.containsKey(element)){
                elements.put(element, elements.get(element) + 1);
            }else{
                elements.put(element, index);
            }
            nameBuilder.append(element.getName());
            if (index > 1) {
                nameBuilder.append("_");
                nameBuilder.append(index);
            }
        }
        name = nameBuilder.toString();
    }

    public TreeMap<Element, Integer> getElementsWithIndexes() {
        return elements;
    }

    public Set<Element> getElementsSet() {
        return elements.keySet();
    }

    public String getName() { return name; }
}
