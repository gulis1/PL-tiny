package sintaxis_abstracta;

public class Instrucciones {


    public class Muchas_Instr extends Instrucciones{

        private Instrucciones instrucciones;
        private Instruccion instruccion;

        public Muchas_Instr(Instrucciones instrucciones, Instruccion instruccion){
            this.instrucciones=instrucciones;
            this.instruccion=instruccion;
        }
    }

    public class No_Instr extends Instrucciones{

        public No_Instr(){}
    }

}
