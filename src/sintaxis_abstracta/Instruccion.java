package sintaxis_abstracta;

public class Instruccion {

    public class Asignacion extends Instruccion{

        private Exp e1, e2;

        public Asignacion(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }
    }

    public class If_then extends Instruccion{

        private Exp exp;
        private Instrucciones is;

        public If_then(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public class If_then_else extends Instruccion{

        private Exp exp;
        private Instrucciones is1, is2;

        public If_then_else(Exp exp, Instrucciones is1, Instrucciones is2) {
            this.exp = exp;
            this.is1 = is1;
            this.is2 = is2;
        }
    }

    public class While extends Instruccion{

        private Exp exp;
        private Instrucciones is;

        public While(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public class Read extends Instruccion{

        private Exp exp;

        public Read(Exp exp) {
            this.exp = exp;
        }
    }

    public class Write extends Instruccion{

        private Exp exp;

        public Write(Exp exp) {
            this.exp = exp;
        }
    }

    public class Nl extends Instruccion{ public Nl() {} }

    public class New extends Instruccion{

        private Exp exp;

        public New(Exp exp) {
            this.exp = exp;
        }
    }

    public class Delete extends Instruccion{

        private Exp exp;

        public Delete(Exp exp) {
            this.exp = exp;
        }
    }

    public class Mix extends Instruccion{

        private Exp exp;
        private Instrucciones is;

        public Mix(Exp exp, Instrucciones is) {
            this.exp = exp;
            this.is = is;
        }
    }

    public class Invoc extends Instruccion {

        private Exp exp;
        private Preales preales;

        public Invoc(Exp exp, Preales preales) {
            this.exp = exp;
            this.preales = preales;
        }
    }
}
