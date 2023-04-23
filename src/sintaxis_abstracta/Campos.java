package sintaxis_abstracta;

import utils.GestorMem;
import utils.TablaSimbolos;

public class Campos extends Nodo {

    public int asig_despl(GestorMem gm){ return -1;};

    public boolean existe_campo(String campo) {
        return false;
    }

    public Tipo tipo_de_campo(String c) {
        return  new Tipo.Ok();
    }

    public int despl_campo(String c) {
        return -1;
    }

    public static class Un_Campo extends Campos{

        private Campo campo;

        public Un_Campo(Campo campo){
            this.campo=campo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.campo.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.campo.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.campo.getT().tipado();
            this.tipo = this.campo.getT();
        }

        @Override
        public int asig_despl(GestorMem gm){
            this.desp = 0;
            this.tipo.asig_espacio_tipo(gm);
            return tipo.tam;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.campo.getT().asig_espacio_tipo2(gm);
        }

        @Override
        public boolean existe_campo(String c) {
            return this.campo.getNombre().equals(c);
        }

        @Override
        public Tipo tipo_de_campo(String c) {
            if (this.campo.getNombre().equals(c)) {
                return this.campo.getT();
            }
            return new Tipo.Error();
        }

        public int despl_campo(String c) {
            if (this.campo.getNombre().equals(c)) {
                return this.desp;
            }
            return -1;
        }
    }

    public static class Muchos_Campos extends Campos {

        private Campos campos;
        private Campo campo;

        public Muchos_Campos(Campos campos,Campo campo){

            this.campos = campos;
            this.campo = campo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.campos.vincula(ts);
            this.campo.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.campos.vincula_ref(ts);
            this.campo.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.campos.tipado();
            this.campo.getT().tipado();
        }

        @Override
        public int asig_despl(GestorMem gm){
            int d = this.campos.asig_despl(gm);
            this.campo.getT().asig_espacio_tipo(gm);
            this.campo.desp = d;
            return d + this.campo.getT().tam;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.campos.asig_espacio_tipo2(gm);
            this.campo.getT().asig_espacio_tipo2(gm);
        }

        @Override
        public boolean existe_campo(String c) {
            if(this.campo.getNombre().equals(c))
                return true;
            else{
                return campos.existe_campo(c);
            }
        }

        @Override
        public Tipo tipo_de_campo(String c) {
            if(this.campo.getNombre().equals(c))
                return this.campo.getT();
            return campos.tipo_de_campo(c);
        }

        @Override
        public int despl_campo(String c) {
            if(this.campo.getNombre().equals(c))
                return this.campo.desp;
            return campos.despl_campo(c);
        }
    }

}
