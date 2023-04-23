package sintaxis_abstracta;

public class Preales extends Nodo{

    public class No_pReal extends Preales { public No_pReal() {} }

    public static class Muchos_pReales extends Preales {

        Exp exp;
        Preales preales;

        public Muchos_pReales(Preales preales, Exp exp) {
            this.exp = exp;
            this.preales = preales;
        }
    }
}
