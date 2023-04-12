package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.ErrorSingleton;
import utils.GestorMem;
import utils.Utils;

public class Instruccion extends Nodo {

    public static class Asignacion extends Instruccion {

        private Exp e1, e2;

        public Asignacion(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.e1.tipado();
            this.e2.tipado();

            if (!Utils.es_desig(e1))
                ErrorSingleton.setError("No es un designador");

            if (Utils.son_compatibles(e1.tipo, e2.tipo))
                this.tipo = new Tipo.Ok();
            else {
                ErrorSingleton.setError("Tipos incompatibles.");
                this.tipo = new Tipo.Error();
            }

        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.e1.gen_cod(maquinap);
            this.e2.gen_cod(maquinap);

            if (Utils.es_desig(this.e2))
                maquinap.ponInstruccion(maquinap.mueve(this.e2.tipo.tam));
            else
                maquinap.ponInstruccion(maquinap.desapilaInd());
        }
    }

    public static class If_then extends Instruccion {

        private Exp exp;
        private Instrucciones is;

        public If_then(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public static class If_then_else extends Instruccion{

        private Exp exp;
        private Instrucciones is1, is2;

        public If_then_else(Exp exp, Instrucciones is1, Instrucciones is2) {
            this.exp = exp;
            this.is1 = is1;
            this.is2 = is2;
        }
    }

    public static class While extends Instruccion{

        private Exp exp;
        private Instrucciones is;

        public While(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public static class Read extends Instruccion{

        private Exp exp;

        public Read(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Write extends Instruccion {

        private Exp exp;

        public Write(Exp exp) {
            this.exp = exp;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            Tipo t = Utils.reff(this.exp.tipo);
            if (t instanceof Tipo.Entero ||
                t instanceof Tipo.Real   ||
                t instanceof Tipo.Cadena){

                this.tipo = new Tipo.Ok();
            }
            else {

                ErrorSingleton.setError("no se printeo");
                this.tipo= new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.write());
        }
    }

    public static class Nl extends Instruccion{ public Nl() {} }

    public static class New extends Instruccion{

        private Exp exp;

        public New(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Delete extends Instruccion{

        private Exp exp;

        public Delete(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Mix extends Instruccion{

        private Exp exp;
        private Instrucciones is;

        public Mix(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public static class Invoc extends Instruccion {

        private Exp exp;
        private Preales preales;

        public Invoc(Exp exp, Preales preales) {
            this.exp = exp;
            this.preales = preales;
        }
    }
}
