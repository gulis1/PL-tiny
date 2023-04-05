package sintaxis_abstracta;

import maquinap.MaquinaP;
import utils.GestorMem;

public abstract class Nodo {

    public Nodo vinculo;
    public Tipo tipo;
    public int nivel;
    public int dir;
    public int tam = -1;

    // Vinculación
    public void vincula(TablaSimbolos ts) { throw new UnsupportedOperationException("Vincula no implementado."); }
    public void vincula_ref(TablaSimbolos ts) { throw new UnsupportedOperationException("Vincula_ref no implementado."); }
    public void vincula_is(TablaSimbolos ts) { throw new UnsupportedOperationException("Vincula_is no implementado."); }

    // Comprobación de tipos
    public void tipado() { throw new UnsupportedOperationException("Tipado no implementado."); };


    // Asignación de espacio
    public void asig_espacio(GestorMem gm) { throw new UnsupportedOperationException("Asig_espacio no implementado."); }
    public void asig_espacio_tipo(GestorMem gm) {
        if (this.tam == -1) {
            this.asig_espacio_tipo1(gm);
            this.asig_espacio_tipo2(gm);
        }
    }
    public void asig_espacio_tipo1(GestorMem gm) { throw new UnsupportedOperationException("Asig_espacio_tipo1 no implementado."); }
    public void asig_espacio_tipo2(GestorMem gm) { throw new UnsupportedOperationException("Asig_espacio_tipo2 no implementado."); }

    // Generación de código
    public void gen_cod(MaquinaP maquinap) { throw new UnsupportedOperationException("Gen_cod no implementado."); };
}
/* Para hacer una suma:

De momento solo tenemos tipo int

- Vincular dec vars
- vincular expresion generica
- Vincular expresion suma
- Vincular write

- Comprobar tipos
- Asignacion de espacio
*/




