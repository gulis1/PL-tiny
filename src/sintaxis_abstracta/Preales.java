package sintaxis_abstracta;

public class Preales {

    public class No_pReal extends Preales { public No_pReal() {} }

    public class Muchos_pReales extends Preales {

        Exp exp;
        Preales preales;

        public Muchos_pReales(Exp exp, Preales preales) {
            this.exp = exp;
            this.preales = preales;
        }
    }
}
