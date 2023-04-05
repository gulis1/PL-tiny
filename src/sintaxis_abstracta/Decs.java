package sintaxis_abstracta;

import utils.GestorMem;

public class Decs extends Nodo {

    public static class Muchas_decs extends Decs {

        Dec dec;
        Decs decs;

        public Muchas_decs(Dec dec, Decs decs) {
            this.dec = dec;
            this.decs = decs;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.dec.vincula(ts);
            this.decs.vincula(ts);
        }

        @Override
        public void tipado() {
            this.decs.tipado();
            this.dec.tipado();

            this.tipo = (this.decs.tipo instanceof Tipo.Ok && this.dec.tipo instanceof Tipo.Ok) ? new Tipo.Ok() : new Tipo.Error();
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.decs.asig_espacio(gm);
            this.dec.asig_espacio(gm);
        }
    }

    public static class No_decs extends Decs {
        public No_decs() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void tipado() {}

        @Override
        public void asig_espacio(GestorMem gm) {}
    }
}
