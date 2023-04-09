package utils;

import sintaxis_abstracta.Exp;
import sintaxis_abstracta.Nodo;
import sintaxis_abstracta.Tipo;

public class Utils {

    public static Nodo reff(Nodo nodo) {
        return nodo.vinculo == null ? nodo: reff(nodo.vinculo);
    }

    public static boolean son_compatibles(Nodo t1, Nodo t2) {

        if (t1.tipo instanceof Tipo.Ref)
            return son_compatibles(reff(t1), t2);
        if (t2.tipo instanceof Tipo.Ref)
            return son_compatibles(t1, reff(t2));
        if (t1.tipo instanceof Tipo.Entero && t2.tipo instanceof Tipo.Entero)
            return true;
        if (t1.tipo instanceof Tipo.Real && t2.tipo instanceof Tipo.Entero)
            return true;
        if (t1.tipo instanceof Tipo.Bool && t2.tipo instanceof Tipo.Bool)
            return true;
        if (t1.tipo instanceof Tipo.String && t2.tipo instanceof Tipo.String)
            return true;
        if (t1.tipo instanceof Tipo.Real && t2.tipo instanceof Tipo.Real)
            return true;
        // TODO Casos array y record

        return false;
    }

    public static boolean es_desig(Nodo nodo) {
        return nodo.vinculo != null;
    }
}
