package sintaxis_abstracta;

import utils.GestorMem;

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
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

    }

    public static class Array extends Tipo {

        private Tipo tipoArray;
        private String tamArray;

        public Array(Tipo tipoArray, String tamArray) {
            this.tipoArray = tipoArray;
            this.tamArray = tamArray;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.tipoArray.vincula(ts);
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
            this.tam = this.tipoArray.tam * Integer.parseInt(this.tamArray);
        }

        public Tipo getT() {
            return this.tipoArray;
        }
    }

    public static class Record extends Tipo {
        private Campos campos;

        public Record(Campos campos){
            this.campos = campos;
        }
    }

    public static class Pointer extends Tipo {
        private Tipo tipo;

        public Pointer(Tipo tipo){
            this.tipo = tipo;
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
