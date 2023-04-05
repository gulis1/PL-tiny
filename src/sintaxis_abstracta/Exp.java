package sintaxis_abstracta;


import jdk.jshell.execution.Util;
import maquinap.MaquinaP;
import utils.ErrorSingleton;
import utils.Utils;

import java.util.HashMap;

public class Exp extends Nodo {

    public static class Null extends Exp { public Null() {} }

    /**
     * EXPRESIONES BÁSICAS
     */
    public static class Id extends Exp {
        private String id;
        public Id(String id) {
            this.id = id;
        }

        @Override
        public void vincula_is(TablaSimbolos ts)  {
            if (ts.contiene(this.id))
                this.vinculo = ts.valor_de(this.id);
            else
                ErrorSingleton.setError("Identificador no encontrado: " + this.id);
        }

        @Override
        public void tipado() {
            if (this.vinculo instanceof Dec.Dec_var)
                this.tipo = this.vinculo.tipo;
            else
                this.tipo = new Tipo.Error();
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
//            maquinap.apilad(this.nivel);
            maquinap.ponInstruccion(maquinap.apilaInt(this.vinculo.dir));
//            maquinap.desapilad(0);
        }
    }

    public static class Exp_int extends Exp {
        private String entero;

        public Exp_int(String entero) {
            this.entero = entero;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() {
            this.tipo= new Tipo.Entero();
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            maquinap.ponInstruccion((maquinap.apilaInt(Integer.parseInt(entero))));
        }
    }

    public class Exp_real extends Exp {
        private String real;

        public Exp_real(String real) {
            this.real = real;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() {
            this.tipo= new Tipo.Real();
        }
    }

    public class Exp_bool extends Exp {
        private String bool;

        public Exp_bool(String bool) {
            this.bool = bool;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }
    }

    public class Exp_cadena extends Exp {
        private String cadena;

        public Exp_cadena(String cadena) {
            this.cadena = cadena;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }
    }

    public class Exp_null extends Exp {

        public Exp_null() {}

        @Override
        public void vincula_is(TablaSimbolos ts) { }
    }

    /**
     * EXPRESIONES BINARIAS
     */
    public class Exp_eq extends Exp {

        private Exp e1, e2;
        public Exp_eq(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_neq extends Exp {

        private Exp e1, e2;
        public Exp_neq(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_gt extends Exp {

        private Exp e1, e2;
        public Exp_gt(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_ge extends Exp {

        private Exp e1, e2;
        public Exp_ge(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_lt extends Exp {

        private Exp e1, e2;
        public Exp_lt(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_le extends Exp {

        private Exp e1, e2;
        public Exp_le(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public static class Exp_suma extends Exp {
        private Exp e1, e2;
        public Exp_suma(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            e1.vincula_is(ts);
            e2.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.e1.tipado();
            this.e2.tipado();

            if (Utils.reff(this.e1).tipo instanceof Tipo.Entero && Utils.reff(this.e2).tipo instanceof Tipo.Entero)
                this.tipo = new Tipo.Entero();

            else if ((Utils.reff(this.e1).tipo instanceof Tipo.Real && Utils.reff(this.e2).tipo instanceof Tipo.Real) ||
                    (Utils.reff(this.e1).tipo instanceof Tipo.Entero && Utils.reff(this.e2).tipo instanceof Tipo.Real) ||
                    (Utils.reff(this.e1).tipo instanceof Tipo.Real && Utils.reff(this.e2).tipo instanceof Tipo.Entero))
            {
                this.tipo = new Tipo.Real();
            }
            else {
                this.tipo = new Tipo.Error();
                utils.ErrorSingleton.setError("los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.e1.gen_cod(maquinap);

            // TODO: convertir floats y esas cosas.
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion((maquinap.apilaInd()));

            maquinap.ponInstruccion(maquinap.suma());
        }
    }

    public class Exp_resta extends Exp {

        private Exp e1, e2;
        public Exp_resta(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_and extends Exp {

        private Exp e1, e2;
        public Exp_and(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_or extends Exp {

        private Exp e1, e2;
        public Exp_or(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_mul extends Exp {

        private Exp e1, e2;
        public Exp_mul(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_div extends Exp {

        private Exp e1, e2;
        public Exp_div(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    public class Exp_mod extends Exp {

        private Exp e1, e2;
        public Exp_mod(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }
    }

    /**
     * EXPRESIONES DE INDEXACIÓN
     */
//    public class Exp_acc extends Exp {
//        private String nombreCampo;
//        private Exp exp;
//
//        public Exp_acc(String nombreCampo, Exp exp) {
//            this.nombreCampo = nombreCampo;
//            this.exp = exp;
//        }
//    }
//
//    public class Exp_index {
//        private Exp e1, e2;
//        public Exp_index(Exp e1, Exp e2) {
//            this.e1 = e1;
//            this.e2 = e2;
//        }
//    }
}
