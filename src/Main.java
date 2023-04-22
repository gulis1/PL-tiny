import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

public class Main {

    public static void main(String[] args) {

//        Dec dec_type = new Dec.Dec_type("tipoMamichan", new Tipo.Cadena());
//        Dec dec_ptr = new Dec.Dec_var(new Tipo.Pointer(new Tipo.Ref("tipoMamichan")), "p");
//        Decs decs = new Decs.Muchas_decs( new Decs.Muchas_decs(new Decs.No_decs(), dec_type), dec_ptr);
//
//        Instruccion i1 = new Instruccion.New(new Exp.Exp_id("p"));
//        Instruccion i2 = new Instruccion.Asignacion(new Exp.Exp_indireccion(new Exp.Exp_id("p")), new Exp.Exp_entero("9"));
//        Instruccion i3 = new Instruccion.Delete(new Exp.Exp_id("p"));
//        Instruccion i4 = new Instruccion.New(new Exp.Exp_id("p"));
//        Instruccion i5 = new Instruccion.Asignacion(new Exp.Exp_indireccion(new Exp.Exp_id("p")), new Exp.Exp_entero("17"));
//        Instruccion i6 = new Instruccion.Write(new Exp.Exp_indireccion(new Exp.Exp_id("p")));
//
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2), i3), i4), i5), i6);

//        Dec dec_var = new Dec.Dec_var(new Tipo.Entero(),"x");
//        Decs decs = new Decs.Muchas_decs( new Decs.No_decs(),dec_var);
//
//        Instruccion i1= new Instruccion.Write(new Exp.Exp_cadena("Escribe un digito"));
//        Instruccion i2 = new Instruccion.Read(new Exp.Exp_id("x"));
//        Instruccion i3 = new Instruccion.Write(new Exp.Exp_cadena("el digito que escribio es:"));
//        Instruccion i4 = new Instruccion.Nl();
//        Instruccion i5 = new Instruccion.Write( new Exp.Exp_id("x"));
//
//        Instrucciones is =new Instrucciones.Muchas_Instr( new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr( new Instrucciones.No_Instr(),i1),i2),i3),i4 ),i5);


//        Dec dec_var = new Dec.Dec_var(new Tipo.Record(new Campos.Un_Campo(new Campo("x", new Tipo.Entero()))),"registro");
//        Decs decs = new Decs.Muchas_decs( new Decs.No_decs(),dec_var);
//        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_acc(new Exp.Exp_id("registro"), "x"), new Exp.Exp_entero("17"));
//        Instruccion i2 = new Instruccion.Write( new Exp.Exp_acc(new Exp.Exp_id("registro"), "x"));
//        Instrucciones is = new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.No_Instr(), i1), i2);

        Dec dec_var = new Dec.Dec_var(new Tipo.Record(new Campos.Muchos_Campos(new Campos.Un_Campo(new Campo("y", new Tipo.Entero())), new Campo("x", new Tipo.Entero()))),"registro");
        Decs decs = new Decs.Muchas_decs( new Decs.No_decs(),dec_var);
        Instruccion i1 = new Instruccion.Asignacion(new Exp.Exp_acc(new Exp.Exp_id("registro"), "x"), new Exp.Exp_entero("17"));
        Instruccion i2 = new Instruccion.Write( new Exp.Exp_acc(new Exp.Exp_id("registro"), "x"));
        Instruccion i3 = new Instruccion.Asignacion(new Exp.Exp_acc(new Exp.Exp_id("registro"), "y"), new Exp.Exp_entero("21"));
        Instruccion i4 = new Instruccion.Write( new Exp.Exp_acc(new Exp.Exp_id("registro"), "y"));
        Instrucciones is =new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr(new Instrucciones.Muchas_Instr( new Instrucciones.No_Instr(),i1),i2),i3),i4 );

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
