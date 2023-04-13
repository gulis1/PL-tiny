import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.GestorMem;

public class Main {

    public static void main(String[] args) {

//        Dec var_x = new Dec.Dec_var(new Tipo.Entero(), "x");
//        Decs decs = new Decs.Muchas_decs(var_x, new Decs.No_decs());
//
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_id("x"), new Exp.Exp_entero("94"));
//        Instruccion i2 = new Instruccion.Write(new Exp.Exp_suma(new Exp.Exp_id("x"), new Exp.Exp_entero("128")));
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2);

//        Dec dec_array = new Dec.Dec_var(new Tipo.Array(new Tipo.Entero(), "3"), "v");
//        Decs decs = new Decs.Muchas_decs(dec_array, new Decs.No_decs());
//
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("1")), new Exp.Exp_entero("7"));
//        Instruccion i2 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("2")), new Exp.Exp_suma(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("1")), new Exp.Exp_entero("3")));
//        Instruccion i3 = new Instruccion.Write(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("2")));
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2), i3);

//        Dec dec = new Dec.Dec_var(new Tipo.Array(new Tipo.Cadena(), "3"), "v");
//        Decs decs = new Decs.Muchas_decs(dec, new Decs.No_decs());
//
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("0")), new Exp.Exp_cadena("mami"));
//        Instruccion i2 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("1")), new Exp.Exp_cadena("chan"));
//        Instruccion i3 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("2")), new Exp.Exp_cadena("<3"));
//        Instruccion i4 = new Instruccion.Write(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_entero("1")));

//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2), i3), i4);

        Dec dec_array = new Dec.Dec_var(new Tipo.Entero(), "x");
        Decs decs = new Decs.Muchas_decs(dec_array, new Decs.No_decs());

        Instruccion i1 = new Instruccion.If_then(new Exp.Exp_bool("false"), new Instruccion.Write(new Exp.Exp_cadena("Mamichan")));
        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1);

        Prog programa = new Prog(decs, is);
        ejecutar(programa);
    }

    private static void ejecutar(Prog programa) {

        TablaSimbolos ts = new TablaSimbolos();
        GestorMem gm = new GestorMem();
        GestorEtiquetado ge = new GestorEtiquetado();
        MaquinaP maquina = new MaquinaP(10, 20 ,10 ,2);

        programa.vincula(ts);
        if (GestorErrores.hayErrores()) {
            GestorErrores.printErrores();
            return;
        }

        programa.tipado();
        if (GestorErrores.hayErrores()) {
            GestorErrores.printErrores();
            return;
        }

        programa.asig_espacio(gm);
        if (GestorErrores.hayErrores()) {
            GestorErrores.printErrores();
            return;
        }

        programa.etiquetado(ge);
        if (GestorErrores.hayErrores()) {
            GestorErrores.printErrores();
            return;
        }

        programa.gen_cod(maquina);
        if (GestorErrores.hayErrores()) {
            GestorErrores.printErrores();
            return;
        }

        maquina.muestraCodigo();
        maquina.ejecuta();
    }
}
