package utils;

import maquinap.MaquinaP;
import sintaxis_abstracta.*;

import java.util.ArrayList;
import java.util.Stack;

public class Utils {

    public static Tipo reff(Tipo tipo) {
        if (Utils.esRef(tipo)) {
            if (tipo.vinculo instanceof Tipo.Ref) return reff((Tipo.Ref) tipo.vinculo); // TODO: probar este caso.
            else return ((Dec.Dec_type)(tipo.vinculo)).getTipo();
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
    public static boolean esRef(Tipo t) { return t instanceof Tipo.Ref; }
    public static boolean esPointer(Tipo t) { return t instanceof Tipo.Pointer; }

    public static Tipo ambos_ok(Tipo t1, Tipo t2) {
        return (!(t1 instanceof Tipo.Error && t2 instanceof Tipo.Error)) ? new Tipo.Ok() : new Tipo.Error();
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
                nodo instanceof  Exp.Exp_ind) ||
                nodo instanceof  Exp.Exp_indireccion;
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


    public static boolean comprobacion_parametros(Preales preals, ParFs pfs) {

        if (preals instanceof Preales.No_pReal && pfs instanceof ParFs.No_Parf)
            return true;

        if (preals instanceof Preales.Muchos_pReales && pfs instanceof ParFs.No_Parf)
            return false;

        if (preals instanceof Preales.No_pReal && pfs instanceof ParFs.Muchos_ParF)
            return false;

        return comprobacion_parametros(((Preales.Muchos_pReales)preals).getPreales(), ((ParFs.Muchos_ParF)pfs).getParFs())
            && comprobacion_parametro(((Preales.Muchos_pReales)preals).getExp(), ((ParFs.Muchos_ParF)pfs).getParF());
    }

    public static boolean comprobacion_parametro(Exp e, ParF pf){

        if (pf instanceof ParF.ParF_Ref)
            return es_desig(e) && son_compatibles(e.tipo, ((ParF.ParF_Ref)pf).getTipoParametro());
        else
            return son_compatibles(e.tipo, ((ParF.ParF_Valor)pf).getTipoParametro());
    }
}
