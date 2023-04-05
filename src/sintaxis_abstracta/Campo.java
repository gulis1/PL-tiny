package sintaxis_abstracta;

public class Campo extends Nodo {

    private String nombre;
    private Tipo tipo;


    public Campo(String nombre,Tipo tipo) {

        this.nombre = nombre;
        this.tipo = tipo;
    }

}
