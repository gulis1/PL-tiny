package sintaxis_abstracta;

import java.util.HashMap;

public class TablaSimbolos {

    private HashMap<String, Nodo> tabla;

    public TablaSimbolos() {
        this.tabla = new HashMap<>();
    }

    public boolean contiene(String clave) {
        return this.tabla.containsKey(clave);
    }

    public void añadir(String clave, Nodo valor) {
        this.tabla.put(clave, valor);
    }

    public Nodo valor_de(String clave) {
        return this.tabla.get(clave);
    }
}

