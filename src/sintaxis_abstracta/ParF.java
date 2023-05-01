package sintaxis_abstracta;

import utils.GestorErrores;
import utils.GestorMem;
import utils.TablaSimbolos;

public class ParF extends Nodo {

    public static class ParF_Valor extends ParF {

        private final String nombre;
        private final Tipo tipoParametro;

        public ParF_Valor(String nombre, Tipo tipo) {
            this.nombre = nombre;
            this.tipoParametro = tipo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            this.tipoParametro.vincula(ts);
            if (ts.contiene(this.nombre))
                GestorErrores.addError("Identificador duplicado: " + this.nombre);
            else ts.añadir(this.nombre, this);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.tipoParametro.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoParametro.tipado();
            this.tipo = tipoParametro.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.dir = gm.dir;
            this.nivel = gm.nivel;
            this.tipoParametro.asig_espacio_tipo(gm);
            gm.dir += this.tipoParametro.tam;
        }

        public Tipo getTipoParametro() {
            return tipoParametro;
        }
    }


    public static class ParF_Ref extends  ParF {

        private final String nombre;
        private final Tipo tipoParametro;

        public ParF_Ref(String nombre, Tipo tipo) {
            this.nombre = nombre;
            this.tipoParametro = tipo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.tipoParametro.vincula(ts);
            if (ts.contiene(this.nombre)) GestorErrores.addError("Identificador duplicado: " + this.nombre);
            else ts.añadir(this.nombre, this);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.tipoParametro.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoParametro.tipado();
            this.tipo = tipoParametro.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.dir = gm.dir;
            this.nivel = gm.nivel;
            this.tipoParametro.asig_espacio_tipo(gm);
            gm.dir++;
        }

        public Tipo getTipoParametro() {
            return tipoParametro;
        }
    }

}
