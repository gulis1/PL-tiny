package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.*;

public class Instruccion extends Nodo {

    public static class Asignacion extends Instruccion {

        private final Exp e1, e2;

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
                GestorErrores.addError("No es un designador");

            if (Utils.son_compatibles(e1.tipo, e2.tipo))
                this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Tipos incompatibles.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.e1.gen_cod(maquinap);
            this.e2.gen_cod(maquinap);

            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if (Utils.es_desig(this.e2))
                maquinap.ponInstruccion(maquinap.mueve(this.e2.tipo.tam));
            else
                maquinap.ponInstruccion(maquinap.desapilaInd());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.e1.etiquetado(ge);
            this.e2.etiquetado(ge);

            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                ge.etq += 1;

            ge.etq += 1;

            this.sig = ge.etq;
        }
    }

    public static class If_then extends Instruccion {

        private final Exp exp;
        private final Instruccion i;

        public If_then(Exp exp, Instruccion i) {
            this.exp = exp;
            this.i = i;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
            this.i.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            this.i.tipado();

            if (Utils.esBool(this.exp.tipo) && Utils.esOk(this.i.tipo)) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Error tipado if_then.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.i.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.irF(this.i.sig));
            this.i.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            ge.etq += 1;
            this.i.etiquetado(ge);

            this.sig = ge.etq;
        }
    }

    public static class If_then_else extends Instruccion {

        private final Exp exp;
        private final Instruccion i1, i2;

        public If_then_else(Exp exp, Instruccion i1, Instruccion i2) {
            this.exp = exp;
            this.i1 = i1;
            this.i2 = i2;
        }
    }

    public static class While extends Instruccion {

        private final Exp exp;
        private final Instruccion i;

        public While(Exp exp, Instruccion i) {
            this.exp = exp;
            this.i = i;
        }
    }

    public static class Read extends Instruccion {

        private final Exp exp;

        public Read(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Write extends Instruccion {

        private final Exp exp;

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
            if (Utils.esEntero(t) ||
                Utils.esReal(t)   ||
                Utils.esCadena(t)) {

                this.tipo = new Tipo.Ok();
            }
            else {

                GestorErrores.addError("no se printeo");
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

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp)) ge.etq += 1;
            ge.etq += 1;

            this.sig = ge.etq;
        }
    }

    public static class Nl extends Instruccion { public Nl() {} }

    public static class New extends Instruccion {

        private final Exp exp;

        public New(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Delete extends Instruccion {

        private final Exp exp;

        public Delete(Exp exp) {
            this.exp = exp;
        }
    }

    public static class Mix extends Instruccion {

        private final Decs decs;
        private final Instrucciones is;

        public Mix(Decs decs, Instrucciones is) {
            this.decs = decs;
            this.is = is;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {

            ts.nuevo_nivel();
            this.decs.vincula(ts);
            // TODO:this.decs.vincula_ref(ts);
            this.is.vincula_is(ts);
            ts.quitar_nivel();
        }

        @Override
        public void tipado() {

            this.decs.tipado();
            this.is.tipado();
            this.tipo = this.is.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {

            int dir_ant = gm.dir;
            this.decs.asig_espacio(gm);
            this.is.asig_espacio(gm);
            gm.dir = dir_ant;
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
           this.is.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;
            this.is.etiquetado(ge);
            this.sig = ge.etq;
        }
    }

    public static class Invoc extends Instruccion {

        private final Exp exp;
        private final Preales preales;

        public Invoc(Exp exp, Preales preales) {
            this.exp = exp;
            this.preales = preales;
        }

    }
}
