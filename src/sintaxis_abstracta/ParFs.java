package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

public class ParFs extends Nodo {

    public static class Muchos_ParF extends ParFs {

        private final ParFs parFs;
        private final ParF parF;

        public Muchos_ParF(ParFs parFs, ParF parF) {
            this.parFs = parFs;
            this.parF = parF;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.parFs.vincula(ts);
            this.parF.vincula(ts);

            this.parFs.vincula_ref(ts);
            this.parF.vincula_ref(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.parFs.vincula_ref(ts);
            this.parF.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.parFs.tipado();
            this.parF.tipado();
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.parFs.asig_espacio(gm);
            this.parF.asig_espacio(gm);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.parFs.etiquetado(ge);
            this.parF.etiquetado(ge);
        }

        public ParFs getParFs() {
            return parFs;
        }

        public ParF getParF() {
            return parF;
        }
    }

    public static class No_Parf extends ParFs {

        public No_Parf() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

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
