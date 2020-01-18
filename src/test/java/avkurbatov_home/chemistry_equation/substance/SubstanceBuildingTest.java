package avkurbatov_home.chemistry_equation.substance;

import avkurbatov_home.chemistry_equation.enums.Element;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SubstanceBuildingTest {

    @Test
    public void shouldBuildByString() throws Exception {
        // given and when
        Substance substance = SubstanceBuilder.buildByString(" c_2.h.lI_3");
        // then
        assertEquals("C_2.H.Li_3", substance.getName());
        assertEquals(ImmutableSet.of(Element.C, Element.H, Element.Li), substance.getElementsSet());
        assertEquals(ImmutableMap.<Element, Integer>builder().put(Element.C, 2).put(Element.H, 1)
                        .put(Element.Li, 3).build(),
                substance.getElementToIndex());
    }

    @Test
    public void shouldNotBuildWithZeroIndex() throws Exception {
        shouldThrowParsingEquationException("c_0");
    }

    @Test
    public void shouldNotBuildWithNotNumericIndex() throws Exception {
        shouldThrowParsingEquationException("c_a");
    }

    private void shouldThrowParsingEquationException(String str) {
        try {
            SubstanceBuilder.buildByString(str);
            fail("Must throw ParsingEquationException");
        } catch (ParsingEquationException e) {
            return;
        } catch (Exception e) {
            fail("Must throw ParsingEquationException");
        }
    }
}
