package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorMem;

public class Prog extends Nodo {

    private Decs decs;
    private Instrucciones is;

    public Prog(Decs decs, Instrucciones is) {
        this.decs = decs;
        this.is = is;
    }

    @Override
    public void vincula(TablaSimbolos ts) {
        // Creamos la TS fuera.
        this.decs.vincula(ts);
        // TODO:
//        this.decs.vincula_ref(ts);
        this.is.vincula_is(ts);
    }

    @Override
    public void tipado() {

        this.decs.tipado();
        this.is.tipado();

        this.tipo = (this.decs.tipo instanceof Tipo.Ok && this.is.tipo instanceof Tipo.Ok) ? new Tipo.Ok() : new Tipo.Error();
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
}
