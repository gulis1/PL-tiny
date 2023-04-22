package sintaxis_abstracta;

import utils.TablaSimbolos;

public class Campo extends Nodo {

    private final String nombre;
    private final Tipo tipo;

    public Campo(String nombre,Tipo tipo) {

        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public void vincula(TablaSimbolos ts) {
        this.tipo.vincula(ts);
    }

    @Override
    public void vincula_ref(TablaSimbolos ts) {
        this.tipo.vincula_ref(ts);
    }

    public Tipo getT() {
        return this.tipo;
    }

    public String getNombre(){
        return this.nombre;
    }
}
