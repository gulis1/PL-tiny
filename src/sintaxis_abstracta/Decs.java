package sintaxis_abstracta;

public class Decs {

    public class Muchas_decs extends Decs {

        Dec dec;
        Decs decs;

        public Muchas_decs(Dec dec, Decs decs) {
            this.dec = dec;
            this.decs = decs;
        }
    }

    public class No_decs extends Decs {
        public No_decs() {}
    }
}
