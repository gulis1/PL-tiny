package sintaxis_abstracta;

import utils.GestorMem;
import utils.TablaSimbolos;
import utils.Utils;

public abstract class Tipo extends Nodo {

    // Tipos aux.
    public static class Ok extends Tipo { public Ok() {} }
    public static class Error extends Tipo { public Error() {} }

    // Tipos base.
    public static class Entero extends Tipo {
        public Entero() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() { this.tipo = new Tipo.Ok(); }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }
    }
    public static class Cadena extends Tipo {

        public Cadena() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }
    }
    public static class Real extends Tipo   {

        public Real() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }
    }
    public static class Bool extends Tipo   {

        public Bool() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }
    }
    public static class Null extends Tipo   {

        public Null() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }
    }

    public static class Array extends Tipo {

        private final Tipo tipoArray;
        private final int tamArray;

        public Array(Tipo tipoArray, String tamArray) {
            this.tipoArray = tipoArray;
            this.tamArray = Integer.parseInt(tamArray);
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.tipoArray.vincula(ts);
        }
        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.tipoArray.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoArray.tipado();
            this.tipo = new Tipo.Ok()   ;
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tipoArray.asig_espacio_tipo1(gm);
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.tipoArray.asig_espacio_tipo2(gm);
            this.tam = this.tipoArray.tam * this.tamArray;
        }

        public Tipo getT() {
            return this.tipoArray;
        }
    }

    public static class Record extends Tipo {

        private final Campos campos;

        public Record(Campos campos){
            this.campos = campos;
        }
    }

    public static class Pointer extends Tipo {

        private final Tipo tipoBase;

        public Pointer(Tipo tipo){
            this.tipoBase = tipo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            if (!Utils.esRef(this.tipoBase))
                this.tipoBase.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {

            if (Utils.esRef(tipoBase.tipo)) {
                // TODO: hacer esto.
            }
            else
                this.tipoBase.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoBase.tipado();
            this.tipo = new Tipo.Ok();
        }

        public Tipo getTipoBase() {
            return this.tipoBase;
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
            if (!Utils.esRef(this.tipoBase))
                this.tipoBase.asig_espacio_tipo1(gm);
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {

            if (Utils.esRef(this.tipoBase)) {
                // TODO: hacer esto.
            }
            else {
                this.tipoBase.asig_espacio_tipo2(gm);
            }
        }
    }

    public static class Ref extends Tipo {
        private Cadena nombre;
        //creo que habria que poner el tipo a la que esta asociado la referencia pero no estoy seguro si basta con el nombre

        public Ref(Cadena nombre){
            this.nombre = nombre;
        }
    }
}
