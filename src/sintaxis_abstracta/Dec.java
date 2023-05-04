package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.*;

public class Dec extends Nodo {

    public static class Dec_var extends Dec {
        private final Tipo T;
        private final String string;

        public Dec_var(Tipo tipo, String string) {
            this.T = tipo;
            this.string = string;
        }

        public Tipo getT() {
            return T;
        }

        public String getString() {
            return string;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            this.T.vincula(ts);
            ts.añadir(this.string, this);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.T.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.T.tipado();
            this.tipo = this.T.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.nivel = gm.nivel;
            this.dir = gm.dir;
            this.T.asig_espacio_tipo(gm);
            gm.dir += this.T.tam;
        }
    }
    public static class Dec_type extends Dec {

        private final Tipo t;
        private String string;
        public Dec_type(String string, Tipo tipo) {
            this.t = tipo;
            this.string = string;
        }

        public Tipo getTipo() {
            return t;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            this.t.vincula(ts);
            ts.añadir(this.string, this);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.t.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.t.tipado();
            this.tipo = this.t.tipo;
        }

        @Override
        public void asig_espacio(GestorMem gm) {
            this.t.asig_espacio_tipo(gm);
        }
    }

    public static class Dec_proc extends Dec {

        private final String id;
        private final ParFs pfs;
        private final Decs decs;
        private final Instrucciones is;
        private int tam_datos;

        public Dec_proc(String str, ParFs pfs, Decs decs, Instrucciones is) {
            this.id = str;
            this.pfs = pfs;
            this.decs = decs;
            this.is = is;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            ts.añadir(this.id, this);

            ts.nuevo_nivel();
            this.pfs.vincula(ts);
            this.decs.vincula(ts);
            this.decs.vincula_ref(ts);
            this.is.vincula_is(ts);
            ts.quitar_nivel();
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.pfs.vincula_ref(ts);
           // TODO: revisar si esto tiene que ir vacio o no,.
        }

        @Override
        public void tipado() {

           this.pfs.tipado();
           this.decs.tipado();
           this.is.tipado();
           this.tipo = Utils.ambos_ok(Utils.ambos_ok(this.pfs.tipo, this.decs.tipo) , this.is.tipo);
        }

        @Override
        public void asig_espacio(GestorMem gm) {

            int dir_anterior = gm.dir;
            gm.nivel++;
            this.nivel = gm.nivel;
            gm.dir = 0;
            this.pfs.asig_espacio(gm);
            this.decs.asig_espacio(gm);
            this.is.asig_espacio(gm);
            this.tam_datos = gm.dir;
            gm.dir = dir_anterior;
            gm.nivel--;
        }

        @Override
        public void gen_cod(MaquinaP maquinap) {
            this.is.gen_cod(maquinap);
            maquinap.ponInstruccion(maquinap.desactiva(this.nivel, this.tam_datos));
            maquinap.ponInstruccion(maquinap.irInd());
            RecolectadorProcs.recolectaProcedimientos(this.decs);
        }

        @Override
        public void etiquetado(GestorEtiquetado ge) {
            this.ini = ge.etq;
            this.is.etiquetado(ge);
            ge.etq += 2;
            RecolectadorProcs.recolectaProcedimientos(this.decs);
        }

        public int get_TamDatos() {
            return this.tam_datos;
        }

        public ParFs getPfs(){
            return this.pfs;
        }
    }
}
