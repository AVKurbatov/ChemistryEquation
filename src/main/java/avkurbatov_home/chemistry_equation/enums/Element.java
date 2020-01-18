package avkurbatov_home.chemistry_equation.enums;

import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;

import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

public enum Element {
    H, He,
    Li, Be, B, C, N, O, F, Ne,
    Na, Mg, Al, Si, P, S, Cl, Ar,
    K, Ca, Sc, Ti, V, Cr, Mn, Fe, Co, Ni, Cu, Zn, Ga, Ge, As, Se, Br, Kr,
    Rb, Sr, Y, Zr, Nb, Mo, Tc, Ru, Rh, Pd, Ag, Cd, In, Sn, Sb, Te, I, Xe,
    Cs, Ba, Hf, Ta, W, Re, Os, Ir, Pt, Au, Hg, Tl, Pb, Bi, Po, At, Rn,
    Fr, Ra, Rf, Db, Sg, Bh, Hs, Mt, Ds, Rg, Cn, Nh, Fl, Mc, Lv, Ts, Og,
    Uue, Ubn,
    La, Ce, Pr, Nd, Pm, Sm, Eu, Gd, Tb, Dy, Ho, Er, Tm, Yb, Lu,
    Ac, Th, Pa, U, Np, Pu, Am, Cm, Bk, Cf, Es, Fm, Md, No, Lr,
    Ubu, Ubb, Ubt, Ubq, Ubp, Ubh;

    public static Element of(String elementName) throws ParsingEquationException {
        elementName = elementName.trim();
        if(elementName.length() == 0) {
            throw new ParsingEquationException(MESSENGER.errorUnableToGetElementName());
        }

        for(Element element : values()) {
            if (element.toString().equalsIgnoreCase(elementName)) {
                return element;
            }
        }
        throw new ParsingEquationException(MESSENGER.errorElementDoesNotExist(elementName));
    }

}
