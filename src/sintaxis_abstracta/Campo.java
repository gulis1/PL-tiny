package sintaxis_abstracta;

public class Campo extends Nodo {

    private final String nombre;
    private final Tipo tipo;

    public Campo(String nombre,Tipo tipo) {

        this.nombre = nombre;
        this.tipo = tipo;
    }

}
