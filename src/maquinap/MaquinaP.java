package maquinap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MaquinaP {
   public static class EAccesoIlegitimo extends RuntimeException {} 
   public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
      public EAccesoAMemoriaNoInicializada(int pc,int dir) {
         super("pinst:"+pc+" dir:"+dir); 
      } 
   } 
   public static class EAccesoFueraDeRango extends RuntimeException {} 
   
   private GestorMemoriaDinamica gestorMemoriaDinamica;
   private GestorPilaActivaciones gestorPilaActivaciones;
    
   private class Valor {
      public int valorInt() {throw new EAccesoIlegitimo();}
      public float valorReal() {throw new EAccesoIlegitimo();}
      public boolean valorBool() {throw new EAccesoIlegitimo();} 
      public String valorCadena() {throw new EAccesoIlegitimo();}
   }

   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }

   private class ValorReal extends Valor {
       private float valor;
       public ValorReal(float valor) { this.valor = valor; }
       public float valorReal() { return this.valor; }
       public String toString() {
           return String.valueOf(valor);
       }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
    private class ValorCadena extends Valor {
        private String valor;
        public ValorCadena(String valor) {
            this.valor = valor;
        }
        public String valorCadena() {return valor;}
        public String toString() {
            return valor;
        }
    }

   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   private ISuma ISUMA;
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()+opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "suma";};
   }
    private IResta IRESTA;
    private class IResta implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt()-opnd2.valorInt()));
            pc++;
        }
        public String toString() {return "resta";};
    }
   private ISumaF ISUMAF;
   private class ISumaF implements Instruccion{
       public void ejecuta(){
           Valor opnd2 = pilaEvaluacion.pop();
           Valor opnd1 = pilaEvaluacion.pop();
           pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
           pc++;
       }
       public String toString(){return  "SumaF";}
   }
    private IRestaF IRESTAF;
    private class IRestaF implements Instruccion{
        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal() - opnd2.valorReal()));
            pc++;
        }
        public String toString(){return  "RestaF";}
    }

   private IMul IMUL;
   private class IMul implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()*opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mul";};
   }

    private IMulF IMULF;
    private class IMulF implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal() * opnd2.valorReal()));
            pc++;
        }
        public String toString() {return "mulF";};
    }

   private IDiv IDIV;
   private class IDiv implements Instruccion{
       public void ejecuta(){
           Valor opnd2 = pilaEvaluacion.pop();
           Valor opnd1 = pilaEvaluacion.pop();
           pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
           pc++;
       }
       public String toString(){return "div";}
   }

    private IDivF IDIVF;
    private class IDivF implements Instruccion{
        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal() / opnd2.valorReal()));
            pc++;
        }
        public String toString(){return "divF";}
    }

   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "and";};
   }
    private IOr IOR;
    private class IOr implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(opnd1.valorBool() || opnd2.valorBool()));
            pc++;
        }
        public String toString() {return "or";};
    }

   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
   }

   private class IApilaFloat implements Instruccion{
       private float valor;
       public IApilaFloat(float valor){this.valor=valor;}
       public void ejecuta(){
           pilaEvaluacion.push(new ValorReal(valor));
           pc++;
       }
       public String toString(){ return "apilaFloat("+valor+")";}
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }

   private class IApilaCadena implements Instruccion {
        private String valor;
        public IApilaCadena(String valor) {
            this.valor = valor;
        }
        public void ejecuta() {
            pilaEvaluacion.push(new ValorCadena(valor));
            pc++;
        }
        public String toString() {return "apilaCadena("+valor+")";};
    }


    private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ira("+dir+")";};
   }

   private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "irf("+dir+")";};
   }
   
   private class IMueve implements Instruccion {
      private int tam;
      public IMueve(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i=0; i < tam; i++) 
                datos[dirDestino+i] = datos[dirOrigen+i]; 
            pc++;
      } 
      public String toString() {return "mueve("+tam+")";};
   }
   private IApilaind IAPILAIND;
   private class IApilaind implements Instruccion {
      public void ejecuta() {
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        if (datos[dir] == null)  throw new EAccesoAMemoriaNoInicializada(pc,dir);
        pilaEvaluacion.push(datos[dir]);
        pc++;
      } 
      public String toString() {return "apilaind";};
   }

   private IDesapilaind IDESAPILAIND;
   private class IDesapilaind implements Instruccion {
      public void ejecuta() {
        Valor valor = pilaEvaluacion.pop();
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        datos[dir] = valor;
        pc++;
      } 
      public String toString() {return "desapilaind";};
   }

   private class IAlloc implements Instruccion {
      private int tam;
      public IAlloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = gestorMemoriaDinamica.alloc(tam);
        pilaEvaluacion.push(new ValorInt(inicio));
        pc++;
      } 
      public String toString() {return "alloc("+tam+")";};
   }

   private class IDealloc implements Instruccion {
      private int tam;
      public IDealloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = pilaEvaluacion.pop().valorInt();
        gestorMemoriaDinamica.free(inicio,tam);
        pc++;
      } 
      public String toString() {return "dealloc("+tam+")";};
   }
   
   private class IActiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       private int dirretorno;
       public IActiva(int nivel, int tamdatos, int dirretorno) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
           this.dirretorno = dirretorno;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
          datos[base] = new ValorInt(dirretorno);
          datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
          pilaEvaluacion.push(new ValorInt(base+2));
          pc++;
       }
       public String toString() {
          return "activa("+nivel+","+tamdatos+","+dirretorno+")";                 
       }
   }
   
   private class IDesactiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       public IDesactiva(int nivel, int tamdatos) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
          gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
          pilaEvaluacion.push(datos[base]); 
          pc++;
       }
       public String toString() {
          return "desactiva("+nivel+","+tamdatos+")";                 
       }

   }
   
   private class IDesapilad implements Instruccion {
       private int nivel;
       public IDesapilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
         pc++;
       }
       public String toString() {
          return "setd("+nivel+")";                 
       }
   }
   private IDup IDUP;
   private class IDup implements Instruccion {
       public void ejecuta() {
           pilaEvaluacion.push(pilaEvaluacion.peek());
           pc++;
       }
       public String toString() {
          return "dup";                 
       }
   }
   private Instruccion ISTOP;
   private class IStop implements Instruccion {
       public void ejecuta() {
           pc = codigoP.size();
       }
       public String toString() {
          return "stop";                 
       }
   }
   
   
   private class IApilad implements Instruccion {
       private int nivel;
       public IApilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
         pc++;
       }
       public String toString() {
          return "apilad("+nivel+")";                 
       }

   }
   
    private Instruccion IIRIND;
    private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "irind";                 
       }
    }

    private Instruccion IWRITE;
    private class IWrite implements Instruccion {
        public void ejecuta() {
           System.out.print(pilaEvaluacion.pop());
           pc++;
        }
        public String toString() {
            return "write";
        }
   }

   private  Instruccion IREADINT;
   private  class  IReadInt implements  Instruccion{
       public void ejecuta(){
           Scanner leer = new Scanner(System.in);
           pilaEvaluacion.push(new ValorInt(leer.nextInt()));
           pc++;
       }
       public String toString() {
           return "Read";
       }
   }

    private  Instruccion IREADFLOAT;
    private  class  IReadFloat implements  Instruccion{
        public void ejecuta(){
            Scanner leer = new Scanner(System.in);
            pilaEvaluacion.push(new ValorReal(leer.nextFloat()));
            pc++;
        }
        public String toString() {
            return "Read";
        }
    }

    private  Instruccion IREADSTRING;
    private  class  IReadString implements  Instruccion{
        public void ejecuta(){
            Scanner leer = new Scanner(System.in);
            pilaEvaluacion.push(new ValorCadena(leer.nextLine()));
            pc++;
        }
        public String toString() {
            return "Read";
        }
    }

    private Instruccion IINT2REAL;
    private class IInt2Real implements Instruccion {
        public void ejecuta() {
            Valor v = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorReal((float) v.valorInt()));
            pc++;
        }
        public String toString() {
            return "Int2Real";
        }
    }



    private IMod IMOD;
    private class IMod implements Instruccion {

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt() % opnd2.valorInt()));
            pc++;
        }
        public String toString(){return "mod";}
    }

    private IMenos IMENOS;
    private class IMenos implements Instruccion{

        public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorInt((-1)*opnd1.valorInt()));

            pc++;

        }

        public String toString(){return "Menos";}
    }

    private IMenosF IMENOSF;
    private class IMenosF implements Instruccion{

        public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorReal((-1)*opnd1.valorReal()));

            pc++;

        }

        public String toString(){return "Menos";}
    }

    private IEqInt IEQINT;
    private class IEqInt implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorInt()==opnd2.valorInt()));

            pc++;
        }
        public String toString(){return "igualdad";}
    }

    private IEqFloat IEQF;
    private class IEqFloat implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorReal()==opnd2.valorReal()));

            pc++;
        }
        public String toString(){return "igualdad";}
    }

    private IEqBool IEQBOOL;
    private class IEqBool implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorBool()==opnd2.valorBool()));

            pc++;
        }
        public String toString(){return "igualdad";}
    }

    private IEqString IEQSTRING;
    private class IEqString implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorCadena().equals(opnd2.valorCadena())));

            pc++;
        }
        public String toString(){return "igualdad";}
    }

    private IGTint IGTINT;
    private class IGTint implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));

            pc++;
        }
        public String toString(){return "Mayor que";}
    }

    private IGTfloat IGTFLOAT;
    private class IGTfloat implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));

            pc++;
        }
        public String toString(){return "Mayor que";}
    }

    private IGTstring IGTSTRING;
    private class IGTstring implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            if(opnd1.valorCadena().compareTo(opnd2.valorCadena()) > 0)
                 pilaEvaluacion.push(new ValorBool(true));
            else
                pilaEvaluacion.push(new ValorBool(false));

            pc++;
        }
        public String toString(){return "Mayor que";}
    }

    private IGTbool IGTBOOL;
    private class IGTbool implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            int op1= (opnd1.valorBool())? 1:0;
            int op2 = (opnd2.valorBool()) ? 1 :0;

            pilaEvaluacion.push(new ValorBool( op1 > op2 ));

            pc++;
        }
        public String toString(){return "Mayor que";}
    }

    private ILTint ILTINT;
    private class ILTint implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));

            pc++;
        }
        public String toString(){return "Menor que";}
    }

    private ILTfloat ILTFLOAT;
    private class ILTfloat implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));

            pc++;
        }
        public String toString(){return "Menor que";}
    }

    private ILTstring ILTSTRING;
    private class ILTstring implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            if(opnd1.valorCadena().compareTo(opnd2.valorCadena()) < 0)
                pilaEvaluacion.push(new ValorBool(true));
            else
                pilaEvaluacion.push(new ValorBool(false));

            pc++;
        }
        public String toString(){return "Menor que";}
    }

    private ILTbool ILTBOOL;
    private class ILTbool implements Instruccion{

        public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();

            int op1= (opnd1.valorBool())? 1:0;
            int op2 = (opnd2.valorBool()) ? 1 :0;

            pilaEvaluacion.push(new ValorBool( op1 < op2 ));

            pc++;
        }
        public String toString(){return "Menor que";}
    }

    private INot INOT;
    private class INot implements Instruccion{

        public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();

            pilaEvaluacion.push(new ValorBool(!opnd1.valorBool()));

            pc++;

        }

        public String toString(){return "Negacion";}
    }



   public Instruccion suma() {return ISUMA;}
   public Instruccion sumaF() {return ISUMAF;}
   public Instruccion resta(){return IRESTA;}
   public Instruccion restaF(){return IRESTAF;}
   public Instruccion mul() {return IMUL;}
   public Instruccion mulF() {return IMULF;}
   public Instruccion div(){return  IDIV;}
   public Instruccion divF(){return IDIVF;}
   public Instruccion mod(){return  IMOD;}
   public Instruccion menos(){return IMENOS;}
   public Instruccion menosF(){return IMENOSF;}
   public Instruccion and() {return IAND;}
   public Instruccion or(){return IOR;}
   public Instruccion eqInt(){return  IEQINT;}
   public Instruccion eqFloat(){return IEQF;}
   public Instruccion eqBool(){return  IEQBOOL;}
   public Instruccion eqString(){return  IEQSTRING;}
   public Instruccion gtInt(){return IGTINT;}
   public Instruccion gtFloat(){return IGTFLOAT;}
   public Instruccion gtString(){return IGTSTRING;}
   public Instruccion gtBool(){return  IGTBOOL;}
   public Instruccion ltInt(){return ILTINT;}
   public Instruccion ltFloat(){return ILTFLOAT;}
   public Instruccion ltString(){return ILTSTRING;}
   public Instruccion ltBool(){return  ILTBOOL;}
   public Instruccion not(){return INOT;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaFloat(float val) {return new IApilaFloat(val);}
   public Instruccion apilaString(String val) {return new IApilaCadena(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion apilaInd() {return IAPILAIND;}
   public Instruccion desapilaInd() {return IDESAPILAIND;}
   public Instruccion mueve(int tam) {return new IMueve(tam);}
   public Instruccion irA(int dir) {return new IIrA(dir);}
   public Instruccion irF(int dir) {return new IIrF(dir);}
   public Instruccion irInd() {return IIRIND;}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion desapilad(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return IDUP;}
   public Instruccion stop() {return ISTOP;}
   public Instruccion readInt(){return IREADINT;}
   public Instruccion readFloat(){return IREADFLOAT;}
   public Instruccion readString(){return IREADSTRING;}
   public Instruccion write() {return IWRITE;}
   public Instruccion int2real(){return IINT2REAL;}
   public void ponInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   private int tamdatos;
   private int tamheap;
   private int ndisplays;
   public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {
      this.tamdatos = tamdatos;
      this.tamheap = tamheap;
      this.ndisplays = ndisplays;
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos+tampila+tamheap];
      this.pc = 0;
      ISUMA = new ISuma();
      ISUMAF = new ISumaF();
      IRESTA = new IResta();
      IRESTAF = new IRestaF();
      IAND = new IAnd();
      IOR= new IOr();
      IMUL = new IMul();
      IMULF = new IMulF();
      IDIV = new IDiv();
      IDIVF = new IDivF();
      IMOD = new IMod();
      IMENOS = new IMenos();
      IMENOSF = new IMenosF();
      IAPILAIND = new IApilaind();
      IDESAPILAIND = new IDesapilaind();
      IIRIND = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      IREADINT = new IReadInt();
      IREADFLOAT = new IReadFloat();
      IREADSTRING = new IReadString();
      IWRITE = new IWrite();
      IINT2REAL = new IInt2Real();
      IEQINT= new IEqInt();
      IEQF = new IEqFloat();
      IEQBOOL = new IEqBool();
      IEQSTRING = new IEqString();
      IGTINT = new IGTint();
      IGTFLOAT = new IGTfloat();
      IGTSTRING = new IGTstring();
      IGTBOOL = new IGTbool();
      ILTINT= new ILTint();
      ILTFLOAT = new ILTfloat();
      ILTSTRING = new ILTstring();
      ILTBOOL = new ILTbool();
      INOT = new INot();
      gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,(tamdatos+tampila)-1,ndisplays);
      gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tampila+tamheap)-1);
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Tam datos:"+tamdatos);  
     System.out.println("Tam heap:"+tamheap); 
     System.out.println("PP:"+gestorPilaActivaciones.pp());      
     System.out.print("Displays:");
     for (int i=1; i <= ndisplays; i++)
         System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
     System.out.println();
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
   
   public static void main(String[] args) {
       MaquinaP m = new MaquinaP(5,10,10,2);
        
          /*
            int x;
            proc store(int v) {
             x = v
            }
           &&
            call store(5)
          */
            
       
       m.ponInstruccion(m.activa(1,1,8));
       m.ponInstruccion(m.dup());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.apilaInt(5));
       m.ponInstruccion(m.desapilaInd());
       m.ponInstruccion(m.desapilad(1));
       m.ponInstruccion(m.irA(9));
       m.ponInstruccion(m.stop());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.apilad(1));
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.mueve(1));
       m.ponInstruccion(m.desactiva(1,1));
       m.ponInstruccion(m.irInd());       
       m.ejecuta();
       m.muestraEstado();
   }
}
