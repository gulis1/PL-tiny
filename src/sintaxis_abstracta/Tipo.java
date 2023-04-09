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
        public void tipado() {
            this.tipo = this;
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }
    }
    public static class String extends Tipo {

        public String() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = this;
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
            this.tipo = this;
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
            this.tipo = this;
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
            this.tipo = this;
        }

    }

    public static class Array extends Tipo {

        private Tipo tipo;
        private String tama;

        public Array(Tipo tipo, String tama) {
            this.tipo = tipo;
            this.tama = tama;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.tipo.vincula(ts);
        }

        @Override
        public void tipado() {
            this.tipo.tipado();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) { }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.tipo.asig_espacio(gm);
            this.tam = this.tipo.tam * Integer.parseInt(this.tama);
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
        private String nombre;
        //creo que habria que poner el tipo a la que esta asociado la referencia pero no estoy seguro si basta con el nombre

        public Ref(String nombre){
            this.nombre = nombre;
        }
    }
}
