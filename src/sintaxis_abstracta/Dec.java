package sintaxis_abstracta;

import utils.GestorMem;

public class Dec extends Nodo {

    public static class Dec_var extends Dec {
        private Tipo T;
        private String string;

        public Dec_var(Tipo tipo, String string) {
            this.T = tipo;
            this.string = string;
        }

        public Tipo getT() {
            return T;
        }

        public void setT(Tipo t) {
            this.T = t;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
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

        String str;
        ParFs pfs;
        Decs decs;
        Instrucciones is;

        public Dec_proc(String str, ParFs pfs, Decs decs, Instrucciones is) {
            this.str = str;
            this.pfs = pfs;
            this.decs = decs;
            this.is = is;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public ParFs getPfs() {
            return pfs;
        }

        public void setPfs(ParFs pfs) {
            this.pfs = pfs;
        }

        public Decs getDecs() {
            return decs;
        }

        public void setDecs(Decs decs) {
            this.decs = decs;
        }

        public Instrucciones getIs() {
            return is;
        }

        public void setIs(Instrucciones is) {
            this.is = is;
        }
    }
}
