package utils;

import sintaxis_abstracta.Nodo;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: comprobar que no haya campos ejn struct con mismo nombre.
public class TablaSimbolos {

    private final ArrayList<HashMap<String, Nodo>> tabla;

    public TablaSimbolos() {
       this.tabla = new ArrayList<>();
       this.nuevo_nivel();
    }

    public boolean contiene(String clave) {

        // Se van recorriendo todos los niveles de la TS en orden inverso.
        for (int i = this.tabla.size() - 1; i >= 0; i--) {
            if (this.tabla.get(i).containsKey(clave)) return true;
        }
        return false;
    }
    public void nuevo_nivel() { this.tabla.add(new HashMap<>()); }
    public void quitar_nivel() { this.tabla.remove(this.tabla.size() - 1); }
    public void añadir(String clave, Nodo valor) {

//        if (this.tabla.get(this.tabla.size() - 1).containsKey(clave))
//            GestorErrores.addError("Identificador duplicado: " + clave);
//        else
            this.tabla.get(tabla.size() - 1).put(clave, valor);
    }
    public Nodo valor_de(String clave) {

        for (int i = this.tabla.size() - 1; i >= 0; i--) {
            if (this.tabla.get(i).containsKey(clave))
                return this.tabla.get(i).get(clave);
        }

        return null;
    }
}

