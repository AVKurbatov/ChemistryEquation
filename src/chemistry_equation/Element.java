package chemistry_equation;


/**
 * Created by Александр on 28.09.2017.
 */
public class Element implements Comparable<Element>{
    final private String name;
    Element(String name) throws ElementsAndSubstancesException{
        String nameTmp = name.trim();
        if(nameTmp.length() > 2)
            throw new ElementsAndSubstancesException();
        for(char c: nameTmp.toCharArray()){
            if(!Character.isLetter(c))
                throw new ElementsAndSubstancesException();
        }

        if (name.length() == 1)
            this.name = "" + Character.toUpperCase(name.charAt(0));
        else
            this.name = "" + Character.toUpperCase(name.charAt(0)) + Character.toLowerCase(name.charAt(1));
    }
    public int compareTo(Element e){
        return name.compareTo(e.name);
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return name;
    }
}
