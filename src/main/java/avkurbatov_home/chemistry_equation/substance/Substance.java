package avkurbatov_home.chemistry_equation.substance;

import avkurbatov_home.chemistry_equation.enums.Element;

import java.util.Map;
import java.util.Set;

public class Substance  {
    /**
     * Name has the same information as elementToIndex so name is not used in equals(), hashCode() and compareTo()
     * */
    private final String name;
    private final Map<Element, Integer> elementToIndex;

    Substance(String name, Map<Element, Integer> elementToIndex) {
        this.name = name;
        this.elementToIndex = elementToIndex;
    }

    public Map<Element, Integer> getElementToIndex() {
        return elementToIndex;
    }

    public Set<Element> getElementsSet() {
        return elementToIndex.keySet();
    }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Substance substance = (Substance) o;

        return elementToIndex != null ? elementToIndex.equals(substance.elementToIndex) : substance.elementToIndex == null;
    }

    @Override
    public int hashCode() {
        return elementToIndex != null ? elementToIndex.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getName();
    }
}
