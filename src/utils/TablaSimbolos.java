package utils;

import sintaxis_abstracta.Nodo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TablaSimbolos {

    private final Stack<ArrayList<HashMap<String, Nodo>>> tabla;

    public TablaSimbolos() {
        this.tabla = new Stack<>();
        this.crear_tabla();
    }

    public boolean contiene(String clave) {

        // Se van recorriendo todos los niveles de la TS en orden inverso.
        for (int i = this.tabla.peek().size() - 1; i >= 0; i--) {
            if (this.tabla.peek().get(i).containsKey(clave)) return true;
        }
        return false;
    }
    public void nuevo_nivel() { this.tabla.peek().add(new HashMap<>()); }
    public void quitar_nivel() { this.tabla.peek().remove(this.tabla.peek().size() - 1); }
    public void añadir(String clave, Nodo valor) { this.tabla.peek().get(tabla.peek().size()  -1).put(clave, valor); }
    public Nodo valor_de(String clave) {

        for (int i = this.tabla.peek().size() - 1; i >= 0; i--) {
            if (this.tabla.peek().get(i).containsKey(clave))
                return this.tabla.peek().get(i).get(clave);
        }

        return null;
    }


    public void crear_tabla() {
        this.tabla.push(new ArrayList<>());
        this.tabla.peek().add(new HashMap<>());
    }
    public void cierra_tabla(boolean esProcedimiento) { this.tabla.pop(); }
}

