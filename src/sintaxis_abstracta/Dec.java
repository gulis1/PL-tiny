package sintaxis_abstracta;

import utils.GestorMem;

public class Dec extends Nodo {

    public static class Dec_var extends Dec {
        private Tipo tipo_dec;
        private String string;

        public Dec_var(Tipo tipo, String string) {
            this.tipo_dec = tipo;
            this.string = string;
        }

        public Tipo getTipo_dec() {
            return tipo_dec;
        }
        public String getString() {
            return string;
        }


        @Override
        public void vincula(TablaSimbolos ts) {

            this.tipo_dec.vincula(ts);
            if (!ts.contiene(this.string)) {
                ts.añadir(this.string, this);
            }
        }

        @Override
        public void tipado() {
            this.tipo_dec.tipado();
            this.tipo = this.tipo_dec.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.nivel = gm.nivel;
            this.dir = gm.dir;
            this.tipo_dec.asig_espacio_tipo(gm);
            gm.dir += this.tipo_dec.tam;
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
