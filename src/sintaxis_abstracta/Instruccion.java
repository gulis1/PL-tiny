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
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.i.sig));
            this.i.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;
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

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
            this.i1.vincula_is(ts);
            this.i2.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            this.i1.tipado();
            this.i2.tipado();

            if (Utils.esBool(this.exp.tipo) && Utils.esOk(this.i1.tipo) && Utils.esOk(this.i2.tipo)) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Error tipado if_else_then.");
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.i1.asig_espacio(gm);
            this.i2.asig_espacio(gm);
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.i2.ini));
            this.i1.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.irA(this.sig));
            this.i2.gen_cod(maquinap);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;
            ge.etq += 1;
            this.i1.etiquetado(ge);
            ge.etq += 1;
            this.i2.etiquetado(ge);

            this.sig = ge.etq;
        }
    }

    public static class While extends Instruccion {

        private final Exp exp;
        private final Instruccion i;

        public While(Exp exp, Instruccion i) {
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
                GestorErrores.addError("Error tipado while.");
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
            if (Utils.es_desig(this.exp))
                maquinap.ponInstruccion(maquinap.apilaInd());
            maquinap.ponInstruccion(maquinap.irF(this.sig));
            this.i.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.irA(this.ini));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;

            this.exp.etiquetado(ge);
            if (Utils.es_desig(this.exp))
                ge.etq +=1;

            ge.etq += 1;
            this.i.etiquetado(ge);
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
            maquinap.ponInstruccion(maquinap.alloc(((Tipo.Pointer)(Utils.reff(this.exp.tipo))).getTipoBase().tam));
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

        private final String id;
        private final Preales preales;

        public Invoc(String id, Preales preales) {
            this.id = id;
            this.preales = preales;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {

            if (ts.contiene(this.id)) this.vinculo = ts.valor_de(this.id);
            else GestorErrores.addError("Identificador no definido: " + this.id);
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
            Dec.Dec_proc dec = (Dec.Dec_proc) this.vinculo;
            ge.etq++;
            etiqueta_params(ge, ((Dec.Dec_proc) this.vinculo).getPfs(), this.preales);
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