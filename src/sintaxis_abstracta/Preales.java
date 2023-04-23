package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

public class Preales extends Nodo{

    public static class No_pReal extends Preales {

        public No_pReal() {}

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

    public static class Muchos_pReales extends Preales {

        Exp exp;
        Preales preales;

        public Muchos_pReales(Preales preales, Exp exp) {
            this.exp = exp;
            this.preales = preales;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {

            this.exp.vincula_is(ts);
            this.preales.vincula_is(ts);
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            this.preales.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.exp.etiquetado(ge);
            this.preales.etiquetado(ge);
        }

        @Override
        public void tipado() {
            this.preales.tipado();
            this.exp.tipado();
        }

        public Exp getExp() {
            return exp;
        }

        public Preales getPreales() {
            return preales;
        }
    }
}
