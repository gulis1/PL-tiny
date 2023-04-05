package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorMem;

public class Instrucciones extends Nodo {

    public static class Muchas_Instr extends Instrucciones {

        private Instrucciones is;
        private Instruccion i;

        public Muchas_Instr(Instrucciones instrucciones, Instruccion instruccion) {
            this.is = instrucciones;
            this.i = instruccion;
        }
    }

    public static class No_Instr extends Instrucciones {

        public No_Instr() {}

        @Override
        public void vincula_is(TablaSimbolos ts) {}

        @Override
        public void tipado() {}

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {}
    }


}
