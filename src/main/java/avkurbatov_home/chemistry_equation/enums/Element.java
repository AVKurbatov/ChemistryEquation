package avkurbatov_home.chemistry_equation.enums;

import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;

import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

public enum Element {
    H("H"), HE("He"),
    LI("Li"), BE("Be("), B("B"), C("C"), N("N"), O("O"), F("F"), NE("Ne"),
    NA("Na"), MG("Mg"), AL("Al"), SI("Si"), P("P"), S("S"), CL("Cl"), AR("Ar"),
    K("K"), CA("Ca"), SC("Sc"), TI("Ti"), V("V"), CR("Cr"), MN("Mn"), FE("Fe"), CO("Co"), NI("Ni"), CU("Cu"), ZN("Zn"),
    GA("Ga"), GE("Ge"), AS("As"), SE("Se"), BR("Br"), KR("Kr"),
    RB("Rb"), SR("Sr"), Y("Y"), ZR("Zr"), NB("Nb"), MB("Mo"), TC("Tc"), RU("Ru"), RH("Rh"), PD("Pd"), AG("Ag"),
    CD("Cd"), IN("In"), SN("Sn"), SB("Sb"), TE("Te"), I("I"), XE("Xe"),
    CS("Cs"), BA("Ba"), HF("Hf"), TA("Ta"), W("W"), RE("Re"), OS("Os"), IR("Ir"), PT("Pt"), AU("Au"), HG("Hg"),
    TL("Tl"), PB("Pb"), BI("Bi"), PO("Po"), AT("At"), RN("Rn"),
    FR("Fr"), RA("Ra"), RF("Rf"), DB("Db"), SG("Sg"), BH("Bh"), HS("Hs"), MT("Mt"), DS("Ds"), RG("Rg"), CN("Cn"),
    NH("Nh"), FL("Fl"), MC("Mc"), LV("Lv"), TS("Ts"), OG("Og"),
    UUE("Uue"), UBN("Ubn"),
    LA("La"), CE("Ce"), PR("Pr"), ND("Nd"), PM("Pm"), SM("Sm"), EU("Eu"), GD("Gd("), TB("Tb"), DY("Dy"), HO("Ho"),
    ER("Er"), TM("Tm"), YB("Yb"), LU("Lu"),
    AC("Ac"), TN("Th"), PA("Pa"), U("U"), NP("Np"), PU("Pu"), AM("Am"), CM("Cm"), BK("Bk"), CF("Cf"), ES("Es"),
    FM("Fm"), MD("Md"), NO("No"), LR("Lr"),
    UBU("Ubu"), UBB("Ubb"), UBT("Ubt"), UBQ("Ubq"), UBP("Ubp"), UBH("Ubh");

    private final String name;

    Element(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Element of(String elementName) throws ParsingEquationException {
        elementName = elementName.trim();
        if(elementName.length() == 0) {
            throw new ParsingEquationException(MESSENGER.errorUnableToGetElementName());
        }

        for(Element element : values()) {
            if (element.getName().equalsIgnoreCase(elementName)) {
                return element;
            }
        }
        throw new ParsingEquationException(MESSENGER.errorElementDoesNotExist(elementName));
    }

}
