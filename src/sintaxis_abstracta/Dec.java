package sintaxis_abstracta;

import utils.GestorMem;
import utils.TablaSimbolos;

public class Dec extends Nodo {

    public static class Dec_var extends Dec {
        private final Tipo T;
        private final String string;

        public Dec_var(Tipo tipo, String string) {
            this.T = tipo;
            this.string = string;
        }

        public Tipo getT() {
            return T;
        }

        public String getString() {
            return string;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            this.T.vincula(ts);
            if (!ts.contiene(this.string)) {
                ts.añadir(this.string, this);
            }
        }

        @Override
        public void tipado() {
            this.T.tipado();
            this.tipo = this.T.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.nivel = gm.nivel;
            this.dir = gm.dir;
            this.T.asig_espacio_tipo(gm);
            gm.dir += this.T.tam;
        }
    }
    public static class Dec_type extends Dec {

        private Tipo tipo;
        private String string;
        Dec_type(String string, Tipo tipo) {
            this.tipo = tipo;
            this.string = string;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            if (!ts.contiene(this.string)) {
                ts.añadir(this.string, this);
            }
        }
    }

    public static class Dec_proc extends Dec {

        private final String str;
        private final ParFs pfs;
        private final Decs decs;
        private final Instrucciones is;

        public Dec_proc(String str, ParFs pfs, Decs decs, Instrucciones is) {
            this.str = str;
            this.pfs = pfs;
            this.decs = decs;
            this.is = is;
        }

        public String getStr() {
            return str;
        }

        public ParFs getPfs() {
            return pfs;
        }

        public Decs getDecs() {
            return decs;
        }

        public Instrucciones getIs() {
            return is;
        }
    }
}
