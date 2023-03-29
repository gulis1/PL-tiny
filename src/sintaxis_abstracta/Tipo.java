package sintaxis_abstracta;

public class Tipo {

    // Tipos base.
    public class Entero extends Tipo { public Entero() {} }
    public class String extends Tipo { public String() {} }
    public class Real extends Tipo { public Real() {} }
    public class Bool extends Tipo { public Bool() {} }

    public class Array extends Tipo {

        private Tipo tipo;
        private String tam;

        public Array(Tipo tipo, String tam) {

            this.tipo=tipo;
            this.tam=tam;
        }
    }

    public class Record extends Tipo{

        private Campos campos;

        public Record(Campos campos){
            this.campos=campos;
        }

    }

    public class Pointer extends Tipo{

        private Tipo tipo;

        public Pointer(Tipo tipo){
            this.tipo=tipo;
        }
    }

    public class Ref extends Tipo {

        private String nombre;
        //creo que habria que poner el tipo a la que esta asociado la referencia pero no estoy seguro si basta con el nombre

        public Ref(String nombre){
            this.nombre = nombre;
        }
    }

}
