package utils;

import sintaxis_abstracta.Exp;
import sintaxis_abstracta.Nodo;
import sintaxis_abstracta.Tipo;

public class Utils {

    public static Tipo reff(Tipo tipo) {
        if (tipo instanceof Tipo.Ref) {
            // TODO
            return tipo; // Provisional
        }
        else return tipo;
    }

    public static boolean son_compatibles(Tipo t1, Tipo t2) {

        if (t1 instanceof Tipo.Ref)
            return son_compatibles(reff(t1), t2);
        if (t2.tipo instanceof Tipo.Ref)
            return son_compatibles(t1, reff(t2));
        if (t1 instanceof Tipo.Entero && t2 instanceof Tipo.Entero)
            return true;
        if (t1 instanceof Tipo.Real && t2 instanceof Tipo.Entero)
            return true;
        if (t1 instanceof Tipo.Bool && t2 instanceof Tipo.Bool)
            return true;
        if (t1 instanceof Tipo.Cadena && t2 instanceof Tipo.Cadena)
            return true;
        if (t1 instanceof Tipo.Real && t2 instanceof Tipo.Real)
            return true;
        // TODO Casos array y record

        return false;
    }

    public static boolean es_desig(Nodo nodo) {
        return (nodo instanceof Exp.Exp_id ||
                nodo instanceof  Exp.Exp_ind);
    }
}
