package sintaxis_abstracta;



public abstract class Dec {

    private Tipo tipo;
    private String string;


    public class Dec_var extends Dec {
        Dec_var(Tipo tipo, String string) {
            super(tipo, string);
        }
    }

    public class Dec_type extends Dec {
        Dec_type(String string, Tipo tipo) {
            super(tipo, string);
        }
    }

//    public class Dec_proc extends Dec{
//
//        Dec_proc()
//    }

    private Dec(Tipo tipo, String string) {
        this.tipo = tipo;
        this.string = string;
    }
}