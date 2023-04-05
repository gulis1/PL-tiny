package sintaxis_abstracta;

public class Campos extends Nodo {


    public class Un_Campo extends Campos{

        private Campo campo;

        public Un_Campo(Campo campo){
            this.campo=campo;
        }
    }

    public class Muchos_Campos extends Campos {

        private Campos campos;
        private Campo campo;

        public Muchos_Campos(Campos campos,Campo campo){

            this.campos = campos;
            this.campo = campo;
        }
    }

}
