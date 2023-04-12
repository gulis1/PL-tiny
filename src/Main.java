import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.ErrorSingleton;
import utils.GestorMem;

public class Main {

    public static void main(String[] args) {
//
//        Dec var_x = new Dec.Dec_var(new Tipo.Entero(), "x");
//        Decs decs = new Decs.Muchas_decs(var_x, new Decs.No_decs());
//
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_id("x"), new Exp.Exp_int("94"));
//        Instruccion i2 = new Instruccion.Write(new Exp.Exp_suma(new Exp.Exp_id("x"), new Exp.Exp_int("128")));
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2);

        Dec dec_array = new Dec.Dec_var(new Tipo.Array(new Tipo.Entero(), "3"), "v");
        Decs decs = new Decs.Muchas_decs(dec_array, new Decs.No_decs());

        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_int("1")), new Exp.Exp_int("7"));
        Instruccion i2 = new Instruccion.Asignacion(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_int("2")), new Exp.Exp_suma(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_int("1")), new Exp.Exp_int("3")));
        Instruccion i3 = new Instruccion.Write(new Exp.Exp_ind(new Exp.Exp_id("v"), new Exp.Exp_int("2")));
        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2), i3);

        Nodo programa = new Prog(decs, is);

        TablaSimbolos ts = new TablaSimbolos();
        GestorMem gm = new GestorMem();
        MaquinaP maquina = new MaquinaP(10, 20 ,10 ,2);

        programa.vincula(ts);
        programa.tipado();

        if (!(programa.tipo instanceof Tipo.Ok)) {
            System.out.println("Tipado incorrecto!!!");
            return;
        }

        programa.asig_espacio(gm);
        programa.gen_cod(maquina);

        maquina.muestraCodigo();
        maquina.ejecuta();
    }
}
