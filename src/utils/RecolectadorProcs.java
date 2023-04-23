package utils;

import sintaxis_abstracta.Dec;
import sintaxis_abstracta.Decs;

import java.util.Stack;

public class RecolectadorProcs {

    private final static Stack<Dec.Dec_proc> procedimientos = new Stack<>();

    public static Dec.Dec_proc pop() {
        return procedimientos.pop();
    }

    public static boolean isEmpty() {
        return procedimientos.isEmpty();
    }

    public static void recolectaProcedimientos(Decs decs) {

        if (decs instanceof Decs.Muchas_decs) {
            Decs.Muchas_decs muchas_decs = (Decs.Muchas_decs) decs;
            recolectaProcedimientos(muchas_decs.getDecs());
            if (muchas_decs.getDec() instanceof Dec.Dec_proc)
                procedimientos.push((Dec.Dec_proc) muchas_decs.getDec());
        }
    }
}
