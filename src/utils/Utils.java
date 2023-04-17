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

    public static boolean esOk(Tipo t) { return t instanceof Tipo.Ok; }
    public static boolean esError(Tipo t) { return t instanceof Tipo.Error; }
    public static boolean esEntero(Tipo t) { return t instanceof Tipo.Entero; }
    public static boolean esReal(Tipo t) {  return t instanceof Tipo.Real; }
    public static boolean esCadena(Tipo t) { return t instanceof Tipo.Cadena; }
    public static boolean esBool(Tipo t) { return t instanceof Tipo.Bool; }
    public static boolean esArray(Tipo t) { return t instanceof Tipo.Array; }

    public static Tipo ambos_ok(Tipo t1, Tipo t2) {
        return (t1 instanceof Tipo.Ok && t2 instanceof Tipo.Ok) ? new Tipo.Ok() : new Tipo.Error();
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


    public static boolean tipado_relacional(Tipo t1,Tipo t2){

        if( reff(t1) instanceof Tipo.Entero && reff(t2) instanceof Tipo.Entero)
            return true;

        if( reff(t1) instanceof Tipo.Real && reff(t2) instanceof Tipo.Real)
            return true;
        if( reff(t1) instanceof Tipo.Entero && reff(t2) instanceof Tipo.Real)
            return true;
        if( reff(t1) instanceof Tipo.Real && reff(t2) instanceof Tipo.Entero)
            return true;
        if( reff(t1) instanceof Tipo.Bool && reff(t2) instanceof Tipo.Bool)
            return true;
        if( reff(t1) instanceof Tipo.Cadena && reff(t2) instanceof Tipo.Cadena)
            return true;

        return false;
    }
    //TODO cuando ya se tengan punturos (no se si se pueden unir a la funcion de arriba)
    public static boolean tipado_igualdad(Tipo t1, Tipo t2){
        return false;
    }
}
