package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;
import utils.Utils;

public class Prog extends Nodo {

    private final Decs decs;
    private final Instrucciones is;

    public Prog(Decs decs, Instrucciones is) {
        this.decs = decs;
        this.is = is;
    }

    @Override
    public void vincula(TablaSimbolos ts) {
        // Creamos la TS fuera.
        this.decs.vincula(ts);
        // TODO: this.decs.vincula_ref(ts);
        this.is.vincula_is(ts);
    }

    @Override
    public void tipado() {

        this.decs.tipado();
        this.is.tipado();

        this.tipo = Utils.ambos_ok(this.decs.tipo, this.is.tipo);
    }

    @Override
    public void asig_espacio(GestorMem gm) {
        this.decs.asig_espacio(gm);
        this.is.asig_espacio(gm);
    }

    @Override
    public void gen_cod(MaquinaP maquinap) {
        this.is.gen_cod(maquinap);
    }

    public Decs getDecs() {
        return decs;
    }

    public Instrucciones getIs() {
        return is;
    }

    @Override
    public void etiquetado(GestorEtiquetado ge) {
        this.is.etiquetado(ge);
    }
}
