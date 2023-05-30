package sintaxis_abstracta;

import utils.GestorErrores;
import utils.GestorMem;
import utils.TablaSimbolos;
import utils.Utils;

public abstract class Tipo extends Nodo {

    // Tipos aux.
    public static class Ok extends Tipo { public Ok() {} }
    public static class Error extends Tipo { public Error() {} }

    // Tipos base.
    public static class Entero extends Tipo {
        public Entero() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() { this.tipo = new Tipo.Ok(); }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }

        @Override
        public String toString() {
            return "entero";
        }
    }
    public static class Cadena extends Tipo {

        public Cadena() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }

        @Override
        public String toString() {
            return "string";
        }
    }
    public static class Real extends Tipo   {

        public Real() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }

        @Override
        public String toString() {
            return "real";
        }
    }
    public static class Bool extends Tipo   {

        public Bool() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) { }

        @Override
        public String toString() {
            return "bool";
        }
    }
    public static class Null extends Tipo   {

        public Null() {}

        @Override
        public void vincula(TablaSimbolos ts) {}

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {
            this.tipo = new Tipo.Ok();
        }

        @Override
        public String toString() {
            return "null";
        }
    }

    public static class Array extends Tipo {

        private final Tipo tipoArray;
        private final int tamArray;

        public Array(Tipo tipoArray, String tamArray) {
            this.tipoArray = tipoArray;
            this.tamArray = Integer.parseInt(tamArray);
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            this.tipoArray.vincula(ts);
        }
        @Override
        public void vincula_ref(TablaSimbolos ts) {
            this.tipoArray.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoArray.tipado();
            this.tipo = new Tipo.Ok()   ;
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tipoArray.asig_espacio_tipo1(gm);
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.tipoArray.asig_espacio_tipo2(gm);
            this.tam = this.tipoArray.tam * this.tamArray;
        }

        public Tipo getT() {
            return this.tipoArray;
        }

        public int getTamArray() {
            return this.tamArray;
        }

        @Override
        public String toString() {
            return String.format("array[%s]", this.tipoArray.toString());
        }
    }

    public static class Record extends Tipo {

        private final Campos campos;

        public Record(Campos campos){
            this.campos = campos;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            campos.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {
            campos.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.campos.tipado();
            this.tipo = new Tipo.Ok();
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = this.campos.asig_despl(gm);
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {
            this.campos.asig_espacio_tipo2(gm);
        }

        public boolean existe_Campo(String c){
            return this.campos.existe_campo(c);
        }

        public Tipo tipo_de_campo(String c){
            return this.campos.tipo_de_campo(c);
        }

        public int desp_campo(String c) {
            return this.campos.despl_campo(c);
        }

        public Campos getCampos() {
            return this.campos;
        }

        @Override
        public String toString() {
            return "record;";
        }
    }

    public static class Pointer extends Tipo {

        private final Tipo tipoBase;

        public Pointer(Tipo tipo){
            this.tipoBase = tipo;
        }

        @Override
        public void vincula(TablaSimbolos ts) {
            if (!Utils.esRef(this.tipoBase))
                this.tipoBase.vincula(ts);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {

            if (Utils.esRef(tipoBase)) {
                Ref ref = (Ref) tipoBase;
                if (ts.contiene(ref.id)) tipoBase.vinculo = ts.valor_de(ref.id);
                else GestorErrores.addError("Identificador no definido: " + ref.id);
            }
            else
                this.tipoBase.vincula_ref(ts);
        }

        @Override
        public void tipado() {
            this.tipoBase.tipado();
            this.tipo = new Tipo.Ok();
        }

        public Tipo getTipoBase() {
            return this.tipoBase;
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {
            this.tam = 1;
            if (!Utils.esRef(this.tipoBase))
                this.tipoBase.asig_espacio_tipo1(gm);
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {

            if (Utils.esRef(this.tipoBase)) {
                Dec.Dec_type dec_tipo = (Dec.Dec_type) this.tipoBase.vinculo;
                dec_tipo.getTipo().asig_espacio_tipo(gm);
//                this.tam = dec_tipo.getTipo().tam;
            }

            else this.tipoBase.asig_espacio_tipo2(gm);
        }

        @Override
        public String toString() {
            return String.format("pointer<%s>", this.tipoBase.toString());
        }
    }

    public static class Ref extends Tipo {

        private final String id;

        public Ref(String identificador){
            this.id = identificador;
        }

        @Override
        public void vincula(TablaSimbolos ts) {

            if (ts.contiene(this.id)) this.vinculo = ts.valor_de(this.id);
            else GestorErrores.addError("Tipo no declarado: " + tipo);
        }

        @Override
        public void vincula_ref(TablaSimbolos ts) {}

        @Override
        public void tipado() {

            if (this.vinculo instanceof Dec.Dec_type) this.tipo = new Tipo.Ok();
            else {
                GestorErrores.addError("Tipado incorrecto: " + id);
                this.tipo = new Tipo.Error();
            }
        }

        @Override
        public void asig_espacio_tipo1(GestorMem gm) {

            Dec.Dec_type dec = (Dec.Dec_type) this.vinculo;
            this.tam = dec.getTipo().tam;
        }

        @Override
        public void asig_espacio_tipo2(GestorMem gm) {}

        @Override
        public String toString() {
            return id;
        }
    }
}
