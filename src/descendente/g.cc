options  {
  STATIC=false;
}
PARSER_BEGIN(ConstructorAST)
package c_ast_descendente;

import asint.TinyASint.Exp;
import asint.TinyASint.Dec;
import asint.TinyASint.Decs;
import asint.TinyASint.Prog;
import semops.SemOps;



public class ConstructorAST {
   private SemOps sem = new SemOps();
}
PARSER_END(ConstructorAST)

TOKEN:{<#letra:["a"-"z","A"-"Z"]>}
TOKEN:{<#digitoPositivo:["1"-"9"]>}
TOKEN:{<#digito:<digitoPositivo>|"0">}
TOKEN:{<#parteEntera:<digitoPositivo> (<digito>)* |"0">}
TOKEN:{<#parteDecimal: (<digito>)* <digitoPositivo>>}
SKIP:{<["\t"," ","\r","\b","\n"]>}
SKIP:{<"@"(~["\n"])*>}

TOKEN:{<begin:("B"|"b")("E"|"e")("G"|"g")("I"|"i")("N"|"n")>}
TOKEN:{<end:("E"|"e")("N"|"n")("D"|"d")>}
TOKEN:{<do:("D"|"d")("O"|"o")>}
TOKEN:{<type:("T"|"t")("Y"|"y")("P"|"p")("E"|"e")>}
TOKEN:{<var:("V"|"v")("A"|"a")("R"|"r")>}
TOKEN:{<proc:("P"|"p")("R"|"r")("O"|"o")("C"|"c")>}
TOKEN:{<if:("I"|"i")("F"|"f")>}
TOKEN:{<then:("T"|"t")("H"|"h")("E"|"e")("N"|"n")>}
TOKEN:{<else:("E"|"e")("L"|"l")("S"|"s")("E"|"e")>}
TOKEN:{<while:("W"|"w")("H"|"h")("I"|"i")("L"|"l")("E"|"e")>}
TOKEN:{<nl:("N"|"n")("L"|"l")>}
TOKEN:{<read:("R"|"r")("E"|"e")("A"|"a")("D"|"d")>}
TOKEN:{<write:("W"|"w")("R"|"r")("I"|"i")("T"|"t")("E"|"e")>}
TOKEN:{<new:("N"|"n")("E"|"e")("W"|"w")>}
TOKEN:{<seq:("S"|"s")("E"|"e")("q"|"q")>}
TOKEN:{<string:("S"|"s")("T"|"t")("R"|"r")("I"|"i")("N"|"n")("G"|"g")>}
TOKEN:{<array:("A"|"a")("R"|"r")("R"|"r")("A"|"a")("Y"|"y")>}
TOKEN:{<record:("R"|"r")("E"|"e")("C"|"c")("O"|"o")("R"|"r")("D"|"d")>}
TOKEN:{<int:("I"|"i")("N"|"n")("T"|"t")>}
TOKEN:{<of:("O"|"o")("F"|"f")>}
TOKEN:{<bool:("B"|"b")("O"|"o")("O"|"o")("L"|"l")>}
TOKEN:{<null:("N"|"n")("U"|"u")("L"|"l")("L"|"l")>}
TOKEN:{<true:("T"|"t")("R"|"r")("U"|"u")("E"|"e")>}
TOKEN:{<false:("F"|"f")("A"|"a")("L"|"l")("S"|"s")("E"|"e")>}
TOKEN:{<and:("A"|"a")("N"|"n")("D"|"d")>}
TOKEN:{<not:("N"|"n")("O"|"o")("T"|"t")>}
TOKEN:{<or:("O"|"o")("R"|"r")>}

TOKEN:{<operadorSuma:\+>}
TOKEN:{<operadorResta:\->}
TOKEN:{<operadorModulo:\%>}
TOKEN:{<operadorAsterisco:\*>}
TOKEN:{<operadorSlash:\/>}
TOKEN:{<operadorSombrero:\^>}
TOKEN:{<operadorPunto:\.>}
TOKEN:{<operadorAsign:\=>}
TOKEN:{<operadorEq:\=\=>}
TOKEN:{<operadorNeq:\!\=>}
TOKEN:{<operadorGt:\>>}
TOKEN:{<operadorGe:\>\=>}
TOKEN:{<operadorLt:\<>}
TOKEN:{<operadorLe:\<\=>}
TOKEN:{<dospuntos:\:>}
TOKEN:{<pap:\(>}
TOKEN:{<pci:\)>}
TOKEN:{<cap:\[>}
TOKEN:{<cci:\]>}
TOKEN:{<pcoma:\;>}
TOKEN:{<coma:\,>}
TOKEN:{<litEntero:[\+,\-]?{<parteEntera>}>}
TOKEN:{<litDecimal:[\+,\-]?{<parteEntera>}\.(0|{<parteDecimal>})>}
TOKEN:{<litString:\'.*\'>}
TOKEN:{<identificador:{<letra>}({<letra>}|{<digito>}|"_")*>}

Prog Init()    : {Prog prog;} {prog=Prog() <EOF> {return prog;}}
Prog Prog()    : {Exp exp; Instrucciones is;}
                    {<evalua> exp=E0() decs=PDonde() {return sem.prog(exp,decs);}}

  Decs PDonde()  : {Decs decs;}
                       {<donde> decs=Decs() {return decs;} |
                       {return null;}}
  Exp E0()       : {Exp exp1, resul;} {exp1=E1() resul=RE0(exp1) {return resul;}}
  Exp RE0(Exp exph) : {char op; Exp exp1; Exp exp;}
                       {op=OP0() exp1=E2() exp=RE0(sem.exp(op,exph,exp1)) {return exp;} |
                       {return exph;}}
  Exp E1()       : {Exp exp2, resul;} {exp2=E2() resul=RE1(exp2) {return resul;}}
  Exp RE1(Exp exph) : {char op; Exp exp2; Exp exp;}
                       {op=OP1() exp2=E2() exp=RE1(sem.exp(op,exph,exp2)) {return exp;} |
                       {return exph;}}
  Exp E2()         : {Exp exp; Token t;}
                        {t=<num> {return sem.num(sem.str(t.image,t.beginLine,t.beginColumn));} |
                         t=<id>  {return sem.id(sem.str(t.image,t.beginLine,t.beginColumn));} |
						"(" exp=E0() ")" {return exp;}}
  char OP0() : {} {"+" {return '+';} |
                   "-" {return '-';}}
  char OP1() : {} {"*" {return '*';} |
                   "/" {return '/';}}
  Decs Decs() : {Dec dec; Decs decs;} {dec=Dec() decs=RDecs(sem.decs_una(dec)) {return decs;}}
  Decs RDecs(Decs decsh) : {Dec dec; Decs decs;}
                             {"," dec=Dec() decs=RDecs(sem.decs_muchas(decsh,dec)) {return decs;} |
                             {return decsh;}}
  Dec Dec() : {Token iden,numb;} {iden=<id> "=" numb=<num>
                                    {return sem.dec(sem.str(iden.image,iden.beginLine,iden.beginColumn),
                                                    sem.str(numb.image,numb.endLine,numb.endColumn));}}