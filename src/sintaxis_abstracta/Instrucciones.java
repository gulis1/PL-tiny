package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.Utils;

public class Instrucciones extends Nodo {

    public static class Muchas_Instr extends Instrucciones {

        private final Instrucciones is;
        private final Instruccion i;

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

            this.tipo = Utils.ambos_ok(this.is.tipo, this.i.tipo);
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

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.is.etiquetado(ge);
            this.i.etiquetado(ge);
        }
    }

    public static class No_Instr extends Instrucciones {

        public No_Instr() {}

        @Override
        public void vincula_is(TablaSimbolos ts) {}

        @Override
        public void tipado() { this.tipo = new Tipo.Ok(); }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {}

        @Override
        public void etiquetado(GestorEtiquetado ge) {}
    }
}
