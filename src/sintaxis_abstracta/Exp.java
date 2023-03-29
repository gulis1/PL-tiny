package sintaxis_abstracta;

public class Exp {

    public class Null extends Exp{ public Null() {} }

    /**
     * EXPRESIONES BÁSICAS
     */
    public class Id extends Exp {
        private String id;

        public Id(String id) {
            this.id = id;
        }
    }

    public class Exp_int extends Exp {
        private String entero;

        public Exp_int(String entero) {
            this.entero = entero;
        }
    }

    public class Exp_real extends Exp {
        private String real;

        public Exp_real(String real) {
            this.real = real;
        }
    }

    public class Exp_bool extends Exp {
        private String bool;

        public Exp_bool(String bool) {
            this.bool = bool;
        }
    }

    public class Exp_cadena extends Exp {
        private String cadena;

        public Exp_cadena(String cadena) {
            this.cadena = cadena;
        }
    }

    /**
     * EXPRESIONES BINARIAS
     */
    private class Exp_bin extends Exp{
        private Exp e1, e2;

        public Exp_bin(Exp e1, Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }
    }

    public class Exp_eq extends Exp_bin {

        public Exp_eq(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_neq extends Exp_bin {

        public Exp_neq(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_gt extends Exp_bin {

        public Exp_gt(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_ge extends Exp_bin {

        public Exp_ge(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_lt extends Exp_bin {

        public Exp_lt(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_le extends Exp_bin {

        public Exp_le(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_suma extends Exp_bin {

        public Exp_suma(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_resta extends Exp_bin {

        public Exp_resta(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_and extends Exp_bin {

        public Exp_and(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_or extends Exp_bin {

        public Exp_or(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_mul extends Exp_bin {

        public Exp_mul(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_div extends Exp_bin {

        public Exp_div(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_mod extends Exp_bin {

        public Exp_mod(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }

    public class Exp_not extends Exp {
        private String not;

        public Exp_not(String not) {
            this.not = not;
        }
    }

    /**
     * EXPRESIONES DE INDEXACIÓN
     */
    public class Exp_acc extends Exp {
        private String nombreCampo;
        private Exp exp;

        public Exp_acc(String nombreCampo, Exp exp) {
            this.nombreCampo = nombreCampo;
            this.exp = exp;
        }
    }

    public class Exp_index extends Exp_bin {

        public Exp_index(Exp e1, Exp e2) {
            super(e1, e2);
        }
    }
}
