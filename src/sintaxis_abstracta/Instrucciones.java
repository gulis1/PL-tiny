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

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.is.vincula_is(ts);
            this.i.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.is.tipado();
            this.i.tipado();

            this.tipo = this.is.tipo instanceof Tipo.Ok && this.i.tipo instanceof Tipo.Ok ? new Tipo.Ok() : new Tipo.Error();
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.is.asig_espacio(gm);
            this.i.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.is.gen_cod(maquinap);
            this.i.gen_cod(maquinap);
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
