package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.*;

import java.util.Stack;

public class Prog extends Nodo {

    private final Decs decs;
    private final Instrucciones is;

    public Prog(Decs decs, Instrucciones is) {
        this.decs = decs;
        this.is = is;
    }

    @Override
    public void vincula(TablaSimbolos ts) {
        this.decs.vincula(ts);
        this.decs.vincula_ref(ts);
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
        maquinap.ponInstruccion(maquinap.stop());

        RecolectadorProcs.recolectaProcedimientos(this.decs);
        while (!RecolectadorProcs.isEmpty()) {
            Dec.Dec_proc proc = RecolectadorProcs.pop();
            proc.gen_cod(maquinap);
        }
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
        ge.etq += 1;
        RecolectadorProcs.recolectaProcedimientos(this.decs);
        while (!RecolectadorProcs.isEmpty()) {
            Dec.Dec_proc proc = RecolectadorProcs.pop();
            proc.etiquetado(ge);
        }
    }
}
