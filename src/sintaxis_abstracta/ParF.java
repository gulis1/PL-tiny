package sintaxis_abstracta;

public class ParF extends Nodo {


    public class ParF_Valor extends ParF {

        private final String nombre;
        private final Tipo tipo;

        public ParF_Valor(String nombre,Tipo tipo) {

            this.nombre = nombre;
            this.tipo = tipo;
        }
    }


    public class ParF_Ref extends  ParF {

        private final String nombre;
        private final Tipo tipo;

        public ParF_Ref(String nombre,Tipo tipo) {

            this.nombre = nombre;
            this.tipo = tipo;
        }
    }

}
