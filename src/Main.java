import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

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
//

//        Decs decs = new Decs.No_decs();
//        Dec dec_mix = new Dec.Dec_var(new Tipo.Entero(), "y");
//        Decs decs_mix = new Decs.Muchas_decs(new Decs.No_decs(), dec_mix);
//        Instruccion imix_1 = new Instruccion.Asignacion(new Exp.Exp_id("y"), new Exp.Exp_entero("5"));
//        Instruccion imix_2 = new Instruccion.Write(new Exp.Exp_id("y"));
//        Instrucciones is_mix = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), imix_1), imix_2);
//        Instruccion mix = new Instruccion.Mix(decs_mix, is_mix);
//        Instruccion i1 = new Instruccion.If_then(new Exp.Exp_neq(new Exp.Exp_real("2.0"),new Exp.Exp_menos(new Exp.Exp_real("2.0")) ), mix);
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1);

//        Decs decs = new Decs.No_decs();
//        Dec dec_mix_1 = new Dec.Dec_var(new Tipo.Entero(), "y");
//        Dec dec_mix_2 = new Dec.Dec_var(new Tipo.Entero(), "x");
//        Decs decs_mix = new Decs.Muchas_decs(new Decs.Muchas_decs(new Decs.No_decs(), dec_mix_2), dec_mix_1);
//
//        Instruccion imix_1 = new Instruccion.Asignacion(new Exp.Exp_id("y"), new Exp.Exp_entero("5"));
//        Instruccion imix_2 = new Instruccion.Write(new Exp.Exp_id("y"));
//        Instrucciones is_mix_1 = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), imix_1), imix_2);
//        Instruccion mix_1 = new Instruccion.Mix(decs_mix, is_mix_1);
//
//        Instruccion imix_3 = new Instruccion.Asignacion(new Exp.Exp_id("x"), new Exp.Exp_entero("10"));
//        Instruccion imix_4 = new Instruccion.Write(new Exp.Exp_id("x"));
//        Instrucciones is_mix_2 = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), imix_3), imix_4);
//        Instruccion mix_2 = new Instruccion.Mix(decs_mix, is_mix_2);
//
//        Instruccion i1 = new Instruccion.If_then_else(new Exp.Exp_bool("false"), mix_1, mix_2);
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1);


        Dec var_x = new Dec.Dec_var(new Tipo.Entero(), "x");
        Decs decs = new Decs.Muchas_decs(new Decs.No_decs(), var_x);

        Instruccion ini_x = new Instruccion.Asignacion(new Exp.Exp_id("x"), new Exp.Exp_entero("0"));
        Instruccion print_x = new Instruccion.Write(new Exp.Exp_id("x"));
        Instrucciones is_x = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), ini_x), print_x);

        Instruccion x_masuno = new Instruccion.Asignacion(new Exp.Exp_id("x") ,new Exp.Exp_suma(new Exp.Exp_id("x"), new Exp.Exp_entero("1")));
        Instruccion mix_buc = new Instruccion.Mix(new Decs.No_decs(), new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(),x_masuno), print_x) );
        Instruccion bucle_while = new Instruccion.While(new Exp.Exp_neq(new Exp.Exp_id("x"), new Exp.Exp_entero("10")), mix_buc);

        Instruccion mix = new Instruccion.Mix(decs, new Instrucciones.Muchas_Instr(is_x, bucle_while) );
        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), mix);

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
