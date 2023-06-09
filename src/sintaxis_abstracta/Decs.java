package sintaxis_abstracta;

import utils.GestorMem;
import utils.TablaSimbolos;
import utils.Utils;

public class Decs extends Nodo {

    public static class Muchas_decs extends Decs {

        private final Dec dec;
        private final Decs decs;

        public Muchas_decs(Decs decs, Dec dec) {
            this.decs = decs;
            this.dec = dec;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.decs.vincula(ts);
            this.dec.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.decs.vincula_ref(ts);
            this.dec.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.decs.tipado();
            this.dec.tipado();

            this.tipo = Utils.ambos_ok(this.decs.tipo, this.dec.tipo);
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.decs.asig_espacio(gm);
            this.dec.asig_espacio(gm);
        }

        public Dec getDec() {
            return dec;
        }

        public Decs getDecs() {
            return decs;
        }
    }

    public static class No_decs extends Decs {

        public No_decs() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() { this.tipo = new Tipo.Ok(); }

        @Override
        public void asig_espacio(GestorMem gm) {}
    }
}
