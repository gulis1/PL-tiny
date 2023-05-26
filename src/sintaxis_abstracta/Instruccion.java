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
                GestorErrores.addError("Asignación: no es un designador");

            if (Utils.son_compatibles(e1.tipo, e2.tipo))
                this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Asignación: tipos incompatibles.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.e1.gen_cod(maquinap);
            this.e2.gen_cod(maquinap);

            // El proceso es un poco distinto si hay que convertit cosas.
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)) {
                if (Utils.es_desig(this.e2)) {
                    maquinap.ponInstruccion(maquinap.desapilaInd());
                    maquinap.ponInstruccion(maquinap.int2real());
                    maquinap.ponInstruccion(maquinap.apilaInd());
                }
                else {
                    maquinap.ponInstruccion(maquinap.int2real());
                    maquinap.ponInstruccion(maquinap.desapilaInd());
                }
            }

            else {
                if (Utils.es_desig(this.e2))
                    maquinap.ponInstruccion(maquinap.mueve(this.e2.tipo.tam));
                //----------------------------------------------------------------
                else
                    maquinap.ponInstruccion(maquinap.desapilaInd());
            }
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.e1.etiquetado(ge);
            this.e2.etiquetado(ge);

            // El proceso es un poco distinto si hay que convertit cosas.
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)) {
                if (Utils.es_desig(this.e2)) ge.etq += 3;
                else ge.etq += 2;
            }
            else ge.etq++;

            this.sig = ge.etq;
        }
    }

    public static class If_then extends Instruccion {

        private final Exp exp;
        private final Instrucciones is;

        public If_then(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
            this.is.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            this.is.tipado();

            if (Utils.esBool(this.exp.tipo) && Utils.esOk(this.is.tipo)) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Error tipado if_then.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.is.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.sig));
            this.is.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;
            ge.etq += 1;
            this.is.etiquetado(ge);

            this.sig = ge.etq;
        }
    }

    public static class If_then_else extends Instruccion {

        private final Exp exp;
        private final Instrucciones is1, is2;

        public If_then_else(Exp exp, Instrucciones is1, Instrucciones is2) {
            this.exp = exp;
            this.is1 = is1;
            this.is2 = is2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
            this.is1.vincula_is(ts);
            this.is2.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            this.is1.tipado();
            this.is2.tipado();

            if (Utils.esBool(this.exp.tipo) && Utils.esOk(this.is1.tipo) && Utils.esOk(this.is2.tipo)) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Error tipado if_else_then.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.is1.asig_espacio(gm);
            this.is2.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.is2.ini));
            this.is1.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.irA(this.sig));
            this.is2.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;
            ge.etq += 1;
            this.is1.etiquetado(ge);
            ge.etq += 1;
            this.is2.etiquetado(ge);

            this.sig = ge.etq;
        }
    }

    public static class While extends Instruccion {

        private final Exp exp;
        private final Instrucciones is;

        public While(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
            this.is.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            this.is.tipado();

            if (Utils.esBool(this.exp.tipo) && Utils.esOk(this.is.tipo)) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Error tipado while.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.is.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.sig));
            this.is.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.irA(this.ini));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;

            ge.etq += 1;
            this.is.etiquetado(ge);
            ge.etq += 1;

            this.sig = ge.etq;
        }
    }

    public static class Read extends Instruccion {

        private final Exp exp;

        public Read(Exp exp) {
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
            if ((Utils.esEntero(t) ||
                    Utils.esReal(t)   ||
                    Utils.esCadena(t)) && Utils.es_desig(this.exp)) {

                this.tipo = new Tipo.Ok();
            }
            else {
                GestorErrores.addError("read: tipos incomaptibles.");
                this.tipo= new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);

            if(Utils.esCadena(this.exp.tipo))
                maquinap.ponInstruccion(maquinap.readString());
            else if (Utils.esEntero(this.exp.tipo))
                maquinap.ponInstruccion(maquinap.readInt());
            else if (Utils.esReal(this.exp.tipo))
                maquinap.ponInstruccion(maquinap.readFloat());

            maquinap.ponInstruccion(maquinap.desapilaInd());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            ge.etq += 2;

            this.sig = ge.etq;
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
                GestorErrores.addError("Write: tipos incomaptibles.");
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

    public static class Nl extends Instruccion {

        public Nl() {}

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            maquinap.ponInstruccion(maquinap.apilaString("\n"));
            maquinap.ponInstruccion(maquinap.write());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;
            ge.etq += 2;

            this.sig = ge.etq;
        }








    }

    public static class New extends Instruccion {

        private final Exp exp;

        public New(Exp exp) {
            this.exp = exp;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
        }

        @Override
        public void tipado() {

            this.exp.tipado();
            if (Utils.esPointer(Utils.reff(this.exp.tipo)))
                this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Tipado alloc no válido.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.alloc(Utils.reff(((Tipo.Pointer)(Utils.reff(this.exp.tipo))).getTipoBase()).tam));
            maquinap.ponInstruccion(maquinap.desapilaInd());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.exp.etiquetado(ge);
            ge.etq += 2;
        }
    }

    public static class Delete extends Instruccion {

        private final Exp exp;

        public Delete(Exp exp) {
            this.exp = exp;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { this.exp.vincula_is(ts); }

        @Override
        public void tipado() {

            this.exp.tipado();
            if (Utils.esPointer(Utils.reff(this.exp.tipo)))
                this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Tipado alloc no válido.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.dealloc(((Tipo.Pointer)(Utils.reff(this.exp.tipo))).getTipoBase().tam));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.exp.etiquetado(ge);
            ge.etq += 2;
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
            this.decs.vincula_ref(ts);
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
            this.exp =exp;
            this.preales = preales;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            if(!(exp instanceof Exp.Exp_id))
                GestorErrores.addError("no es identificador");
            else {
                String id = ((Exp.Exp_id)exp).getId();
                if (ts.contiene(id))this.vinculo = ts.valor_de(id);
                else GestorErrores.addError("Identificador no definido: " + id);

            }
            this.preales.vincula_is(ts);
        }

        @Override
        public void tipado() {

            if (this.vinculo instanceof Dec.Dec_proc) {

                this.preales.tipado();
                Dec.Dec_proc dec = (Dec.Dec_proc) this.vinculo;
                if (Utils.comprobacion_parametros(this.preales,dec.getPfs())) this.tipo = new Tipo.Ok();
                else GestorErrores.addError("Los parámetros no son compatibles.");
            }
            else GestorErrores.addError("No es un procedimiento.");
        }

        @Override
        public void asig_espacio(GestorMem gm) {}

        @Override
        public void gen_cod(MaquinaP maquinap) {

            Dec.Dec_proc dec = (Dec.Dec_proc) this.vinculo;
            maquinap.ponInstruccion(maquinap.activa(dec.nivel, dec.get_TamDatos(), this.sig));
            this.gen_cod_params(maquinap, ((Dec.Dec_proc) this.vinculo).getPfs(), this.preales);
            maquinap.ponInstruccion(maquinap.desapilad(dec.nivel));
            maquinap.ponInstruccion(maquinap.irA(dec.ini));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;
            ge.etq++;
            this.etiqueta_params(ge, ((Dec.Dec_proc) this.vinculo).getPfs(), this.preales);
            ge.etq += 2;
            this.sig = ge.etq;
        }

        private void gen_cod_params(MaquinaP maquinap, ParFs parfs, Preales preales) {

            if (parfs instanceof ParFs.No_Parf && preales instanceof Preales.No_pReal)
                return;

            if (parfs instanceof ParFs.Muchos_ParF && preales instanceof Preales.Muchos_pReales) {
                gen_cod_params(maquinap, ((ParFs.Muchos_ParF) parfs).getParFs(), ((Preales.Muchos_pReales) preales).getPreales());
                gen_cod_paso(maquinap, ((Preales.Muchos_pReales) preales).getExp(), ((ParFs.Muchos_ParF) parfs).getParF());
            }
        }

        private void gen_cod_paso(MaquinaP maquinap, Exp exp, ParF parf) {

            maquinap.ponInstruccion(maquinap.dup());
            maquinap.ponInstruccion(maquinap.apilaInt(parf.dir));
            maquinap.ponInstruccion(maquinap.suma());
            exp.gen_cod(maquinap);
            if (Utils.es_desig(exp) && parf instanceof ParF.ParF_Valor)
                maquinap.ponInstruccion(maquinap.mueve(((ParF.ParF_Valor) parf).getTipoParametro().tam));
            else
                maquinap.ponInstruccion(maquinap.desapilaInd());
        }

        private void etiqueta_params(GestorEtiquetado ge, ParFs parfs, Preales preales) {

            if (parfs instanceof ParFs.No_Parf && preales instanceof Preales.No_pReal)
                return;

            if (parfs instanceof ParFs.Muchos_ParF && preales instanceof Preales.Muchos_pReales) {
                etiqueta_params(ge, ((ParFs.Muchos_ParF) parfs).getParFs(), ((Preales.Muchos_pReales) preales).getPreales());
                etiqueta_paso(ge, ((Preales.Muchos_pReales) preales).getExp(), ((ParFs.Muchos_ParF) parfs).getParF());
            }
        }

        private void etiqueta_paso(GestorEtiquetado ge, Exp exp, ParF parf) {

            ge.etq += 3;
            exp.etiquetado(ge);
            ge.etq++;
        }
    }
}