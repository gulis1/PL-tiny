package sintaxis_abstracta;

import utils.TablaSimbolos;

public class Campo extends Nodo {

    private final String nombre;
    private final Tipo tipoBase;

    public Campo(String nombre, Tipo tipo) {

        this.nombre = nombre;
        this.tipoBase = tipo;
    }

    @Override
    public void vincula(TablaSimbolos ts) {
        this.tipoBase.vincula(ts);
    }

    @Override
    public void vincula_ref(TablaSimbolos ts) {
        this.tipoBase.vincula_ref(ts);
    }

    public Tipo getT() {
        return this.tipoBase;
    }

    public String getNombre(){
        return this.nombre;
    }
}
