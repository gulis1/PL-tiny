import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

public class Main {

    public static void main(String[] args) {

       // Dec dec_type = new Dec.Dec_type("tipoMamichan", new Tipo.Cadena());
       // Dec dec_ptr = new Dec.Dec_var(new Tipo.Pointer(new Tipo.Ref("tipoMamichan")), "p");
       // Decs decs = new Decs.Muchas_decs( new Decs.Muchas_decs(new Decs.No_decs(), dec_type), dec_ptr);

       // Instruccion i1 = new Instruccion.New(new Exp.Exp_id("p"));
       // Instruccion i2 = new Instruccion.Asignacion(new Exp.Exp_indireccion(new Exp.Exp_id("p")), new Exp.Exp_entero("9"));
       // Instruccion i3 = new Instruccion.Delete(new Exp.Exp_id("p"));
       // Instruccion i4 = new Instruccion.New(new Exp.Exp_id("p"));
       // Instruccion i5 = new Instruccion.Asignacion(new Exp.Exp_indireccion(new Exp.Exp_id("p")), new Exp.Exp_entero("17"));
       // Instruccion i6 = new Instruccion.Write(new Exp.Exp_indireccion(new Exp.Exp_id("p")));

//        Dec dec_proc = new Dec.Dec_var(new Tipo.Entero(), "y");
//
//        Instruccion iproc1 = new Instruccion.Write(new Exp.Exp_cadena("Hola, "));
//        Instruccion iproc2 = new Instruccion.Write(new Exp.Exp_id("nombre"));
//        Instruccion iproc3 = new Instruccion.Asignacion(new Exp.Exp_id("y"), new Exp.Exp_entero("3"));
//        Instruccion iproc4 = new Instruccion.Asignacion(new Exp.Exp_id("x"), new Exp.Exp_suma(new Exp.Exp_id("x"), new Exp.Exp_id("y")));
//        Instrucciones isProc = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), iproc1), iproc2), iproc3), iproc4);
//        ParFs parFs = new ParFs.Muchos_ParF(new ParFs.Muchos_ParF(new ParFs.No_Parf(), new ParF.ParF_Valor("nombre", new Tipo.Cadena())), new ParF.ParF_Ref("x", new Tipo.Entero()));
//        Dec f1 = new Dec.Dec_proc("saludar", parFs, new Decs.Muchas_decs(new Decs.No_decs(), dec_proc), isProc);
//        Decs decs = new Decs.Muchas_decs(new Decs.Muchas_decs(new Decs.No_decs(), new Dec.Dec_var(new Tipo.Entero(), "num")), f1);
//
//        Preales preales = new Preales.Muchos_pReales(new Preales.Muchos_pReales(new Preales.No_pReal(), new Exp.Exp_cadena("Mamichan")), new Exp.Exp_id("num"));
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_id("num"), new Exp.Exp_entero("4"));
//        Instruccion i2 = new Instruccion.Invoc("saludar", preales);
//        Instruccion i3 = new Instruccion.Write(new Exp.Exp_id("num"));
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2), i3);
//        Prog prog = new Prog(decs, is);

        Decs decs = new Decs.No_decs();

        Dec dec_mix = new Dec.Dec_var(new Tipo.Entero(), "y");
        Decs decs_mix = new Decs.Muchas_decs(new Decs.No_decs(), dec_mix);

        Instruccion imix_1 = new Instruccion.Asignacion(new Exp.Exp_id("y"), new Exp.Exp_entero("5"));
        Instruccion imix_2 = new Instruccion.Write(new Exp.Exp_id("y"));
        Instrucciones is_mix = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), imix_1), imix_2);
        Instruccion mix = new Instruccion.Mix(decs_mix, is_mix);


        Instruccion i1 = new Instruccion.If_then(new Exp.Exp_neq(new Exp.Exp_entero("4"),new Exp.Exp_real("4.46454")) , mix);
        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1);



        Prog prog = new Prog(decs, is);


        ejecutar(prog);
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
