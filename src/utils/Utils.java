package utils;

import sintaxis_abstracta.*;

public class Utils {

    public static Tipo reff(Tipo tipo) {
        if (Utils.esRef(tipo)) {
            if (tipo.vinculo instanceof Tipo.Ref) return reff((Tipo.Ref) tipo.vinculo); // TODO: probar este caso.
            else return ((Dec.Dec_type)(tipo.vinculo)).getTipo();
        }
        else if (esPointer(tipo)) { return reff(((Tipo.Pointer)tipo).getTipoBase()); }
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
    public static boolean esRecord(Tipo t) { return t instanceof Tipo.Record; }
    public static boolean esNull(Tipo t) { return t instanceof Tipo.Null; }


    public static Tipo ambos_ok(Tipo t1, Tipo t2) {
        return (!(t1 instanceof Tipo.Error && t2 instanceof Tipo.Error)) ? new Tipo.Ok() : new Tipo.Error();
    }

    public static boolean son_compatibles(Tipo t1, Tipo t2) {

        if (esRef(t1))
            return son_compatibles(reff(t1), t2);
        if (esRef(t2))
            return son_compatibles(t1, reff(t2));


        if (esEntero(t1) && esEntero(t2))
            return true;
        if (esReal(t1) && esEntero(t2))
            return true;
        if (esBool(t1) && esBool(t2))
            return true;
        if (esCadena(t1) && esCadena(t2))
            return true;
        if (esReal(t1) && esRef(t2))
            return true;
        if (esPointer(t1) && esNull(t2))
            return true;
        if (esPointer(t1) && esPointer(t2))
//            return son_compatibles(((Tipo.Pointer)t1).getTipoBase(), ((Tipo.Pointer)t2).getTipoBase());
            return true;
        if(esNull(t1) && esPointer(t2))
            return true;
        if (esArray(t1) && esArray(t2)) {
            Tipo.Array array1 = (Tipo.Array) t1;
            Tipo.Array array2 = (Tipo.Array) t2;

            return (array1.getTamArray() == array1.getTamArray()) && son_compatibles(array1.getT(), array2.getT());
        }
        if (esRecord(t1) && esRecord(t2))
            return t1 == t2;
//            return campos_compatibles(((Tipo.Record) t1).getCampos(), ((Tipo.Record) t2).getCampos());

        return false;
    }

    public static boolean es_desig(Nodo nodo) {
        return (nodo instanceof  Exp.Exp_id ||
                nodo instanceof  Exp.Exp_ind) ||
                nodo instanceof  Exp.Exp_indireccion ||
                nodo instanceof  Exp.Exp_acc;
    }

    public static boolean tipado_eq(Tipo t1, Tipo t2) {

        if (tipado_ord(t1, t2))
            return true;
        if (esPointer(reff(t1)) && esPointer(reff(t2)))
            return true;
        if (esPointer(reff(t1)) && esNull(reff(t2)))
            return true;
        if(esNull(reff(t1)) && esPointer(reff(t2)))
            return  true;
        if (esNull(reff(t1)) && esNull(reff(t2)))
            return true;

        return false;
    }

    public static boolean tipado_ord(Tipo t1, Tipo t2) {

        if( esEntero(reff(t1)) && esEntero(reff(t2)))
            return true;
        if( esReal(reff(t1)) && esReal(reff(t2)))
            return true;
        if( esEntero(reff(t1)) && esReal(reff(t2)))
            return true;
        if( esReal(reff(t1)) && esEntero(reff(t2)))
            return true;
        if( esBool(reff(t1)) && esBool(reff(t2)))
            return true;
        if( esCadena(reff(t1)) && esCadena(reff(t2)))
            return true;

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

    public static Exp get_exp_cmp(Exp exp1, Exp exp2, String operador) {

        switch (operador) {
            case "==": return new Exp.Exp_eq(exp1, exp2);
            case "!=": return new Exp.Exp_neq(exp1, exp2);
            case ">":  return new Exp.Exp_gt(exp1, exp2);
            case ">=": return new Exp.Exp_ge(exp1, exp2);
            case "<":  return new Exp.Exp_lt(exp1, exp2);
            case "<=": return new Exp.Exp_le(exp1, exp2);
            default: throw new IllegalArgumentException("Operador no válido.");
        }
    }

    public static Exp get_exp_op3(Exp exp1, Exp exp2, String operador) {

        switch (operador) {
            case "*": return new Exp.Exp_mul(exp1, exp2);
            case "/": return new Exp.Exp_div(exp1, exp2);
            case "%":  return new Exp.Exp_mod(exp1, exp2);
            default: throw new IllegalArgumentException("Operador no válido.");
        }
    }

    public static boolean campos_compatibles(Campos campos1, Campos campos2) {

        if (campos1 instanceof Campos.Muchos_Campos && campos2 instanceof Campos.Muchos_Campos) {
            Campos.Muchos_Campos muchos_campos1 = (Campos.Muchos_Campos) campos1;
            Campos.Muchos_Campos muchos_campos2 = (Campos.Muchos_Campos) campos2;
            return campos_compatibles(muchos_campos1.getCampos(), muchos_campos2.getCampos())
                    && son_compatibles(muchos_campos1.getCampo().getT(), muchos_campos2.getCampo().getT());
        }
        else if (campos1 instanceof Campos.Un_Campo && campos2 instanceof Campos.Un_Campo) {
            Campos.Un_Campo unCampo1 = (Campos.Un_Campo) campos1;
            Campos.Un_Campo unCampo2 = (Campos.Un_Campo) campos2;

            return son_compatibles(unCampo1.getCampo().getT(), unCampo2.getCampo().getT());
        }
        else
            return false;
    }
}
