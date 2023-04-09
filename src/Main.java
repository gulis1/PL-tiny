import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorMem;

public class Main {

    public static void main(String[] args) {

        Dec var_x = new Dec.Dec_var(new Tipo.Entero(), "x");
        Decs decs = new Decs.Muchas_decs(var_x, new Decs.No_decs());

        Instruccion i1 = new Instruccion.Asignacion(new Exp.Id("x"), new Exp.Exp_entero("7"));
        Instruccion i2 = new Instruccion.Write(new Exp.Exp_mod(new Exp.Id("x"), new Exp.Exp_entero("4")));
        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2);

        Nodo programa = new Prog(decs, is);

        TablaSimbolos ts = new TablaSimbolos();
        GestorMem gm = new GestorMem();
        MaquinaP maquina = new MaquinaP(10, 20 ,10 ,2);

        programa.vincula(ts);
        programa.tipado();
        programa.asig_espacio(gm);
        programa.gen_cod(maquina);

        maquina.muestraCodigo();
        maquina.ejecuta();
    }
}
