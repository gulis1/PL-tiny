package sintaxis_abstracta;


import maquinap.MaquinaP;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.TablaSimbolos;
import utils.Utils;

public class Exp extends Nodo {

    /**
     * EXPRESIONES BÁSICAS
     */
    public static class Exp_id extends Exp {

        private final String id;
        public Exp_id(String id) {
            this.id = id;
        }

        @Override
        public void vincula_is(TablaSimbolos ts)  {
            if (ts.contiene(this.id))
                this.vinculo = ts.valor_de(this.id);
            else
                GestorErrores.addError("Identificador no encontrado: " + this.id);
        }

        @Override
        public void tipado() {
            if (this.vinculo instanceof Dec.Dec_var)
                this.tipo = ((Dec.Dec_var) this.vinculo).getT();
            else if (this.vinculo instanceof ParF.ParF_Valor)
                this.tipo = ((ParF.ParF_Valor) this.vinculo).getTipoParametro();
            else if (this.vinculo instanceof ParF.ParF_Ref)
                this.tipo = ((ParF.ParF_Ref) this.vinculo).getTipoParametro();
            else {
                GestorErrores.addError("Error: no es una dec");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            if (this.vinculo.nivel == 0)
                maquinap.ponInstruccion(maquinap.apilaInt(this.vinculo.dir));
            else {
                maquinap.ponInstruccion(maquinap.apilad(this.vinculo.nivel));
                maquinap.ponInstruccion(maquinap.apilaInt(this.vinculo.dir));
                maquinap.ponInstruccion(maquinap.suma());
                if (this.vinculo instanceof ParF.ParF_Ref) maquinap.ponInstruccion(maquinap.apilaInd());
            }
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            if (this.vinculo.nivel == 0)
                ge.etq++;
            else {
                ge.etq += 3;
                if (this.vinculo instanceof ParF.ParF_Ref) ge.etq++;
            }
        }
    }

    public static class Exp_entero extends Exp {

        private final int entero;

        public Exp_entero(String entero) {
            this.entero = Integer.parseInt(entero);
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() {
            this.tipo = new Tipo.Entero();
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            maquinap.ponInstruccion((maquinap.apilaInt(entero)));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) { ge.etq += 1; }
    }

    public static class Exp_real extends Exp {

        private final float real;

        public Exp_real(String real) {
            this.real = Float.parseFloat(real);
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() {
            this.tipo = new Tipo.Real();
        }

        public void gen_cod(MaquinaP maquinaP) {
            maquinaP.ponInstruccion(maquinaP.apilaFloat(this.real));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) { ge.etq += 1; }
    }

    public static class Exp_bool extends Exp {

        private final boolean bool;

        public Exp_bool(String bool) {
            this.bool = Boolean.parseBoolean(bool);
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() { this.tipo = new Tipo.Bool(); }

        public void gen_cod(MaquinaP maquinaP) {
            maquinaP.ponInstruccion(maquinaP.apilaBool(this.bool));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) { ge.etq += 1; }
    }

    public static class Exp_cadena extends Exp {

        private final String cadena;

        public Exp_cadena(String cadena) {
            this.cadena = cadena;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() { this.tipo = new Tipo.Cadena(); }

        @Override
        public void gen_cod(MaquinaP maquinaP) {
            maquinaP.ponInstruccion(maquinaP.apilaString(this.cadena));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) { ge.etq += 1; }
    }

    public static class Exp_null extends Exp {

        public Exp_null() {}

        @Override
        public void vincula_is(TablaSimbolos ts) { }

        @Override
        public void tipado() { this.tipo = new Tipo.Null(); }

        @Override
        public void gen_cod(MaquinaP maquinaP) {
            maquinaP.ponInstruccion(maquinaP.apilaInt(0));
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) { ge.etq += 1; }
    }

    /*
        EXPRESIONES UNARIAS
     */


    public static class Exp_not extends Exp{

        private final Exp e1;
        public Exp_not(Exp e1){
            this.e1=e1;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.e1.tipado();

            if(Utils.esBool(Utils.reff(this.e1.tipo)))
                this.tipo= new Tipo.Bool();
            else{
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 no es operable");
            }

        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            maquinap.ponInstruccion(maquinap.not());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            ge.etq+=1;

        }
    }


    public static class Exp_menos extends Exp {

        private final Exp e1;
        public Exp_menos(Exp e1){
            this.e1=e1;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.e1.tipado();

            if(Utils.esEntero(Utils.reff(this.e1.tipo)))
                this.tipo= new Tipo.Entero();
            else if(Utils.esReal(Utils.reff(this.e1.tipo)))
                this.tipo= new Tipo.Real();
            else{
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 no es operable");
            }

        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esEntero(Utils.reff(this.e1.tipo)))
                maquinap.ponInstruccion(maquinap.menos());
            else if(Utils.esReal(Utils.reff(this.e1.tipo)))
                maquinap.ponInstruccion(maquinap.menosF());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            ge.etq+=1;

        }
    }

    /**
     * EXPRESIONES BINARIAS
     */
    public static class Exp_eq extends Exp {

        private final Exp e1, e2;
        public Exp_eq(Exp e1, Exp e2) {
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

            if(Utils.tipado_eq(this.e1.tipo,this.e2.tipo)){
                this.tipo = new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.eqInt());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
               || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=1;

            ge.etq+=1;
        }
    }

    public static class Exp_neq extends Exp {

        private final Exp e1, e2;
        public Exp_neq(Exp e1, Exp e2) {
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

            if(Utils.tipado_eq(this.e1.tipo,this.e2.tipo)){
                this.tipo= new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.eqInt());


            maquinap.ponInstruccion(maquinap.not());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
                    || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=1;

            ge.etq+=2;
        }
    }

    public static class Exp_gt extends Exp {

        private final Exp e1, e2;
        public Exp_gt(Exp e1, Exp e2) {
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

            if(Utils.tipado_ord(this.e1.tipo,this.e2.tipo)){
                this.tipo= new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }


        }


        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());

            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.gtInt());

        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
                    || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=1;

            ge.etq+=1;
        }
    }

    public static class Exp_ge extends Exp {

        private final Exp e1, e2;
        public Exp_ge(Exp e1, Exp e2) {
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

            if(Utils.tipado_ord(this.e1.tipo,this.e2.tipo)){
                this.tipo= new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            //Se hace el > primero
            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.gtInt());


            //Se vuelven a meter los valores para hacer el ==
            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.eqInt());


            //se hace la or logica de los resultados previos
            maquinap.ponInstruccion(maquinap.or());

        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            //etiquetado correspondiente a >
            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            //etiquetado correspòndiente a ==
            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
                    || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=2;

            //se le suman las dos operaciones anteriores (> y == ) y la operacion de or logica
            ge.etq+=3;

        }
    }

    public static class Exp_lt extends Exp {

        private final Exp e1, e2;
        public Exp_lt(Exp e1, Exp e2) {
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

            if(Utils.tipado_ord(this.e1.tipo,this.e2.tipo)){
                this.tipo= new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.ltBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.ltString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.ltInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.ltFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.ltFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.ltFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.ltInt());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
                    || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=1;

            ge.etq++;
        }
    }

    public static class Exp_le extends Exp {

        private final Exp e1, e2;
        public Exp_le(Exp e1, Exp e2) {
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

            if(Utils.tipado_ord(this.e1.tipo,this.e2.tipo)){
                this.tipo= new Tipo.Bool();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }


        }


        @Override
        public void gen_cod(MaquinaP maquinap) {

            //Se hace el > primero
            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.gtString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.gtFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.gtInt());


            //se niega lo anterior para hacer la operacion de <
            maquinap.ponInstruccion(maquinap.not());

            //Se vuelven a meter los valores para hacer el ==
            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if(Utils.esBool(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqBool());
            else if (Utils.esCadena(this.e1.tipo))
                maquinap.ponInstruccion(maquinap.eqString());
            else if (Utils.esEntero(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqInt());
            else if (Utils.esReal(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else if(Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.eqFloat());
            else // Estamos comparando punteros
                maquinap.ponInstruccion(maquinap.eqInt());


            //se hace la or logica de los resultados previos
            maquinap.ponInstruccion(maquinap.or());

        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            //etiquetado correspondiente a >
            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            //etiquetado correspòndiente a ==
            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            if(Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo)
                    || Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq +=2;

            //se le suman las tres operaciones anteriores (> , != y  == ) y la operacion de or logica
            ge.etq+=4;

        }
    }

    public static class Exp_suma extends Exp {

        private final Exp e1, e2;
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

            if (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo)))
                this.tipo = new Tipo.Entero();

            else if ((Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo))))
            {
                this.tipo = new Tipo.Real();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion((maquinap.apilaInd()));
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if (Utils.esEntero(this.tipo))
                maquinap.ponInstruccion(maquinap.suma());
            else
                maquinap.ponInstruccion(maquinap.sumaF());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                ge.etq += 1;

            ge.etq += 1;
        }
    }

    public static class Exp_resta extends Exp {

        private final Exp e1, e2;
        public Exp_resta(Exp e1, Exp e2) {
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

            if (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo)))
                this.tipo = new Tipo.Entero();

            else if ((Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo))))
            {
                this.tipo = new Tipo.Real();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion((maquinap.apilaInd()));
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if (Utils.esEntero(this.tipo))
                maquinap.ponInstruccion(maquinap.resta());
            else
                maquinap.ponInstruccion(maquinap.restaF());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                ge.etq += 1;

            ge.etq += 1;
        }
    }

    public static class Exp_and extends Exp {

        private final Exp e1, e2;
        public Exp_and(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.e1.vincula_is(ts);
            this.e2.vincula_is(ts);
        }

        public void tipado() {
            this.e1.tipado();
            this.e2.tipado();

            if(Utils.esBool(Utils.reff(this.e1.tipo)) && Utils.esBool(Utils.reff(this.e2.tipo)))
                this.tipo=new Tipo.Bool();

            else{
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }

        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            maquinap.ponInstruccion(maquinap.and());

        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            ge.etq+=1;
        }
    }

    public static class Exp_or extends Exp {

        private final Exp e1, e2;
        public Exp_or(Exp e1, Exp e2) {
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

            if(Utils.esBool(Utils.reff(this.e1.tipo)) && Utils.esBool(Utils.reff(this.e2.tipo)))
                this.tipo=new Tipo.Bool();

            else{
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }

        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            maquinap.ponInstruccion(maquinap.or());

        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            ge.etq+=1;
        }
    }

    public static class Exp_mul extends Exp {

        private final Exp e1, e2;
        public Exp_mul(Exp e1, Exp e2) {
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

            if (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo)))
                this.tipo = new Tipo.Entero();

            else if ((Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo))))
            {
                this.tipo = new Tipo.Real();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion((maquinap.apilaInd()));
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if (Utils.esEntero(this.tipo))
                maquinap.ponInstruccion(maquinap.mul());
            else
                maquinap.ponInstruccion(maquinap.mulF());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                ge.etq += 1;

            ge.etq += 1;
        }
    }

    public static class Exp_div extends Exp {

        private final Exp e1, e2;
        public Exp_div(Exp e1, Exp e2) {
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

            if (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo)))
                this.tipo = new Tipo.Entero();

            else if ((Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esReal(Utils.reff(this.e2.tipo))) ||
                    (Utils.esReal(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo))))
            {
                this.tipo = new Tipo.Real();
            }
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables.");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion((maquinap.apilaInd()));
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                maquinap.ponInstruccion(maquinap.int2real());

            if (Utils.esEntero(this.tipo))
                maquinap.ponInstruccion(maquinap.div());
            else
                maquinap.ponInstruccion(maquinap.divF());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;
            if (Utils.esEntero(this.e1.tipo) && Utils.esReal(this.e2.tipo))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;
            if (Utils.esReal(this.e1.tipo) && Utils.esEntero(this.e2.tipo))
                ge.etq += 1;

            ge.etq += 1;
        }
    }

    public static class Exp_mod extends Exp {

        private final Exp e1, e2;
        public Exp_mod(Exp e1, Exp e2) {
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

            if (Utils.esEntero(Utils.reff(this.e1.tipo)) && Utils.esEntero(Utils.reff(this.e2.tipo)))
                this.tipo = new Tipo.Entero();
            else {
                this.tipo = new Tipo.Error();
                GestorErrores.addError("Los tipos de E0 y E1 no son operables.");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {

            this.e1.gen_cod(maquinap);
            if (Utils.es_desig(e1))
                maquinap.ponInstruccion(maquinap.apilaInd());

            this.e2.gen_cod(maquinap);
            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            maquinap.ponInstruccion(maquinap.mod());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {

            this.e1.etiquetado(ge);
            if (Utils.es_desig(e1))
                ge.etq += 1;

            this.e2.etiquetado(ge);
            if (Utils.es_desig(e2))
                ge.etq += 1;

            ge.etq += 1;
        }
    }

    /**
     * EXPRESIONES DE INDEXACIÓN
     */
    public static class Exp_ind extends Exp {

        private final Exp e1, e2;
        public Exp_ind(Exp e1, Exp e2) {
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

            if (!Utils.esArray(Utils.reff(this.e1.tipo))) {
                GestorErrores.addError("Tipo array incorrecto");
                return;
            }

            if (!(Utils.esEntero(Utils.reff(this.e2.tipo)))) {
                GestorErrores.addError("Tamaño de array incorrecto");
                return;
            }

            this.tipo = ((Tipo.Array) e1.tipo).getT();
        }


        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.e1.gen_cod(maquinap);
            this.e2.gen_cod(maquinap);

            if (Utils.es_desig(e2))
                maquinap.ponInstruccion(maquinap.apilaInd());

            maquinap.ponInstruccion(maquinap.apilaInt(((Tipo.Array)this.e1.tipo).getT().tam));
            maquinap.ponInstruccion(maquinap.mul());
            maquinap.ponInstruccion(maquinap.suma());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.e1.etiquetado(ge);
            this.e2.etiquetado(ge);

            if (Utils.es_desig(e2))
                ge.etq++;

            ge.etq += 3;
        }
    }

    public static class Exp_indireccion extends Exp {

        Exp exp;

        public Exp_indireccion(Exp exp) {
            this.exp = exp;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) { this.exp.vincula_is(ts); }

        @Override
        public void tipado() {

            this.exp.tipado();
            if (Utils.esPointer(Utils.reff(this.exp.tipo)))
                this.tipo = ((Tipo.Pointer) Utils.reff(this.exp.tipo)).getTipoBase();
            else
                GestorErrores.addError("El operador ^ solo se puede usar con punteros.");
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.apilaInd());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.exp.etiquetado(ge);
            ge.etq += 1;
        }
    }

    public static class Exp_acc extends Exp {

        private final Exp exp;
        private final String str;
        public Exp_acc(Exp exp, String str) {
            this.exp = exp;
            this.str = str;
        }

        @Override
        public void vincula_is(TablaSimbolos ts) {
            this.exp.vincula_is(ts);
        }

        @Override
        public void tipado() {
            this.exp.tipado();
            if (Utils.esRecord(Utils.reff(this.exp.tipo))){
                if( ((Tipo.Record) Utils.reff(this.exp.tipo)).existe_Campo(this.str)) {
                    this.tipo = ((Tipo.Record) Utils.reff(this.exp.tipo)).tipo_de_campo(this.str);
                }
                else{
                    GestorErrores.addError("No existe el campos en el registro");
                }
            }
            else{
                GestorErrores.addError("El operador . solo se puede usar con registros.");
            }
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.exp.gen_cod(maquinap);
            int desp = ((Tipo.Record) Utils.reff(this.exp.tipo)).desp_campo(this.str);
            maquinap.ponInstruccion(maquinap.apilaInt(desp));
            maquinap.ponInstruccion(maquinap.suma());
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.exp.etiquetado(ge);
            ge.etq += 2;
        }
    }
}
