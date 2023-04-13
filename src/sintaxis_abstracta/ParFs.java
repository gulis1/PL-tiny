package sintaxis_abstracta;

public class ParFs extends Nodo {

    public class Muchos_ParF extends ParFs {

        private final ParFs parFs;
        private final ParF parF;

        public Muchos_ParF(ParFs parFs, ParF parF) {

            this.parFs = parFs;
            this.parF = parF;
        }
    }

    public class No_Parf extends ParFs {

        public No_Parf() {}

    }


}
