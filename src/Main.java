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


        Instruccion iproc1 = new Instruccion.Write(new Exp.Exp_cadena("Hola, "));
        Instruccion iproc2 = new Instruccion.Write(new Exp.Exp_id("nombre"));
        Instrucciones isProc = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), iproc1), iproc2);
        ParFs parFs = new ParFs.Muchos_ParF(new ParFs.No_Parf(), new ParF.ParF_Valor("nombre", new Tipo.Cadena()));
        Dec f1 = new Dec.Dec_proc("saludar", parFs, new Decs.No_decs(), isProc);
        Decs decs = new Decs.Muchas_decs(new Decs.No_decs(), f1);

        Preales preales = new Preales.Muchos_pReales(new Preales.No_pReal(), new Exp.Exp_cadena("Mamichan"));
        Instruccion i1 = new Instruccion.Invoc("saludar", preales);
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
