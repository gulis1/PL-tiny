options  {
  STATIC=false;
}
PARSER_BEGIN(ConstructorAST)
package descendente;

import sintaxis_abstracta.*;
import utils.Utils;



public class ConstructorAST {
   
}
PARSER_END(ConstructorAST)

TOKEN:{<#letra:["a"-"z","A"-"Z"]>}
TOKEN:{<#digitoPositivo:["1"-"9"]>}
TOKEN:{<#digito:<digitoPositivo>|"0">}
TOKEN:{<#parteEntera:<digitoPositivo> (<digito>)* |"0">}
TOKEN:{<#parteDecimal: (<digito>)* <digitoPositivo>>}
SKIP:{<["\t"," ","\r","\b","\n"]>}
SKIP:{<"@"(~["\n"])*>}

TOKEN:{<BEGIN:("B"|"b")("E"|"e")("G"|"g")("I"|"i")("N"|"n")>}
TOKEN:{<END:("E"|"e")("N"|"n")("D"|"d")>}
TOKEN:{<DO:("D"|"d")("O"|"o")>}
TOKEN:{<TYPE:("T"|"t")("Y"|"y")("P"|"p")("E"|"e")>}
TOKEN:{<VAR:("V"|"v")("A"|"a")("R"|"r")>}
TOKEN:{<PROC:("P"|"p")("R"|"r")("O"|"o")("C"|"c")>}
TOKEN:{<IF:("I"|"i")("F"|"f")>}
TOKEN:{<THEN:("T"|"t")("H"|"h")("E"|"e")("N"|"n")>}
TOKEN:{<ELSE:("E"|"e")("L"|"l")("S"|"s")("E"|"e")>}
TOKEN:{<WHILE:("W"|"w")("H"|"h")("I"|"i")("L"|"l")("E"|"e")>}
TOKEN:{<NL:("N"|"n")("L"|"l")>}
TOKEN:{<READ:("R"|"r")("E"|"e")("A"|"a")("D"|"d")>}
TOKEN:{<WRITE:("W"|"w")("R"|"r")("I"|"i")("T"|"t")("E"|"e")>}
TOKEN:{<NEW:("N"|"n")("E"|"e")("W"|"w")>}
TOKEN:{<DELETE:("D"|"d")("E"|"e")("L"|"l")("E"|"e")("T"|"t")("E"|"e")>}
TOKEN:{<SEQ:("S"|"s")("E"|"e")("q"|"q")>}
TOKEN:{<STRING:("S"|"s")("T"|"t")("R"|"r")("I"|"i")("N"|"n")("G"|"g")>}
TOKEN:{<ARRAY:("A"|"a")("R"|"r")("R"|"r")("A"|"a")("Y"|"y")>}
TOKEN:{<RECORD:("R"|"r")("E"|"e")("C"|"c")("O"|"o")("R"|"r")("D"|"d")>}
TOKEN:{<INT:("I"|"i")("N"|"n")("T"|"t")>}
TOKEN:{<REAL:("R"|"r")("E"|"e")("A"|"a")("L"|"l")>}
TOKEN:{<OF:("O"|"o")("F"|"f")>}
TOKEN:{<BOOL:("B"|"b")("O"|"o")("O"|"o")("L"|"l")>}
TOKEN:{<NULL:("N"|"n")("U"|"u")("L"|"l")("L"|"l")>}
TOKEN:{<TRUE:("T"|"t")("R"|"r")("U"|"u")("E"|"e")>}
TOKEN:{<FALSE:("F"|"f")("A"|"a")("L"|"l")("S"|"s")("E"|"e")>}
TOKEN:{<AND:("A"|"a")("N"|"n")("D"|"d")>}
TOKEN:{<NOT:("N"|"n")("O"|"o")("T"|"t")>}
TOKEN:{<OR:("O"|"o")("R"|"r")>}

TOKEN:{<OP_SUMA:"+">}
TOKEN:{<OP_RESTA:"-">}
TOKEN:{<MODULO:"%">}
TOKEN:{<ASTERISCO:"*">}
TOKEN:{<SLASH:"/">}
TOKEN:{<SOMBRERO:"^">}
TOKEN:{<PUNTO:".">}
TOKEN:{<OP_ASIG:"=">}
TOKEN:{<EQ:"==">}
TOKEN:{<NEQ:"!=">}
TOKEN:{<GT:">">}
TOKEN:{<GE:">=">}
TOKEN:{<LT:"<">}
TOKEN:{<LE:"<=">}
TOKEN:{<DOSPUNTOS:":">}
TOKEN:{<PAP:"(">}
TOKEN:{<PCI:")">}
TOKEN:{<CAP:"[">}
TOKEN:{<CCI:"]">}
TOKEN:{<PCOMA:";">}
TOKEN:{<COMA:",">}
TOKEN:{<LIT_ENTERO:("+"|"-")?<parteEntera>>}
TOKEN:{<LIT_DECIMAL:("+"|"-")?<parteEntera>"."("0"|<parteDecimal>)>}
TOKEN:{<LIT_STRING:"\'"(~["\t","\b","\r","\n"])"\'">}
TOKEN:{<ID:<letra>(<letra>|<digito>|"_")*>}



Prog PROG(): {Decs decs; Instrucciones is;} {decs=DECS() <BEGIN> is=INSTRUCCIONES() <END> <PUNTO> {return new Prog(decs, is);}}

Tipo TIPO():   {Tipo t;}  {

    <STRING> {return new Tipo.Cadena();}  |
    t=TARRAY() {return t;}          |
    t=TRECORD() {return t;}         |
    <INT> {return new Tipo.Entero();}     |
    <BOOL> {return new Tipo.Bool();}      |
    t=REF() {return t;}             |
    t=POINTER() {return t;}         |
    <REAL> {return new Tipo.Real();}      |
    <NULL> {return new Tipo.Null();}
}

Tipo POINTER(): {Tipo t;} {<SOMBRERO> t=TIPO() {return new Tipo.Pointer(t);}}

Tipo REF(): {Token t;} {t=<ID> {return new Tipo.Ref(t.image);}}

Tipo TARRAY(): {Token ent; Tipo tipo;} { <ARRAY> <CAP> ent=<LIT_ENTERO> <CCI> <OF> tipo=TIPO() {return new Tipo.Array(tipo, ent.image);}}


Tipo TRECORD(): {Campos campos;} { <RECORD> campos=CAMPOS() <END> {return new  Tipo.Record(campos);}}

Campos CAMPOS(): {Campo campo; Campos resul;} {campo=CAMPO() resul=RCAMPOS(new Campos.Un_Campo(campo)) {return resul;}}

Campos RCAMPOS(Campos campoh): {Campo campo; Campos resul;}{
    campo=CAMPO() resul=RCAMPOS(new Campos.Muchos_Campos(campoh,campo)){return resul;} |
        {return campoh;} 

}

Campo CAMPO(): {Token id; Tipo tipo;} {id=<ID> <DOSPUNTOS> tipo=TIPO() <PCOMA> {return new Campo(id.image, tipo);}}

Decs DECS(): {Decs  resul;} {resul=RDECS(new Decs.No_decs()) {return resul;}}

Decs RDECS(Decs dech): {Dec dec;Decs resul;} {
    dec=DEC() resul=RDECS(new Decs.Muchas_decs(dech, dec)){return resul;} |
        {return dech;} 
}

Dec DEC(): {Dec dec;} {

    dec=DEC_VAR() {return dec;}  |
    dec=DEC_TYPE() {return dec;} |
    dec=DEC_PROC() {return dec;}
}

Dec DEC_VAR(): {Tipo tipo; Token id;} {<VAR> id=<ID> <DOSPUNTOS> tipo=TIPO() <PCOMA> {return new Dec.Dec_var(tipo, id.image);}}

Dec DEC_TYPE(): {Tipo tipo; Token id;} {<TYPE> id=<ID> <DOSPUNTOS> tipo=TIPO() <PCOMA> {return new  Dec.Dec_type(id.image, tipo);}}

Dec DEC_PROC(): {Token id; ParFs parfs; Decs decs; Instrucciones is;} {<PROC> id=<ID> <PAP> parfs=PFORMALES() <PCI> decs=DECS() <BEGIN> is=INSTRUCCIONES() <END> <PCOMA> {return new Dec.Dec_proc(id.image, parfs, decs, is);} }


ParFs PFORMALES(): {ParFs resul;} {resul=RPFORMALES(new ParFs.No_Parf()) {return resul;}}

ParFs RPFORMALES(ParFs parfh): {ParF parf; ParFs resul;} {
    parf=PFORMAL() <COMA> resul=RPFORMALES(new ParFs.Muchos_ParF(parfh,parf)) {return resul;} |
        {return parfh;}
}


ParF PFORMAL(): {ParF parf;} {
    parf=PFR_VALOR() {return parf;}
    parf=PFR_REF() {return parf;}
}

ParF PFR_VALOR(): {Token id; Tipo tipo;} {id=<ID> <DOSPUNTOS> tipo=TIPO() {return new ParF.ParF_Valor(id.image, tipo);}}
ParF PFR_REF(): {Token id; Tipo tipo;} {<VAR> id=<ID> <DOSPUNTOS> tipo=TIPO() {return new ParF.ParF_Ref(id.image, tipo);}}

Exp E0(): {Exp e1, resul;}  {e1=E1() resul=RE0(e1) {return resul;}}

Exp RE0(Exp eh): {Exp e1; String op;} {

    op=OP0() e1=E1() {return Utils.get_exp_cmp(eh, e1, op);} |
     {return eh;}
}


Exp E1(): {Exp resul, e2, expre1;} {e2=E2() expre1=RE1(e2) resul=RE1P(expre1) {return resul;}}

Exp RE1(Exp eh): {Exp e2;} {
    <OP_SUMA> e2=E2() {return new Exp.Exp_suma(eh, e2);} |
     {return eh;}

}

Exp RE1P(Exp eh): {Exp e1,resul;} {

    <OP_RESTA> e1=E1() resul=RE1P(new Exp.Exp_suma(eh, e1)) {return resul;} |
     {return eh;}
}

Exp E2(): {Exp e3, resul;} {e3=E3() resul=RE2(e3) {return resul;}}

Exp RE2(Exp eh): {Exp ed;} {
    <AND> ed=E3() {return new Exp.Exp_and(eh, ed);} |
    <OR> ed=E2() {return new Exp.Exp_or(eh, ed);} |
      {return eh;}
}

Exp E3(): {Exp e4, resul;} {e4=E4() resul=RE3(e4) {return resul;}}

Exp RE3(Exp eh): {Exp e4, resul; String op;} {
    op=OP3() e4=E4() resul=RE3(Utils.get_exp_op3(eh, e4, op)) {return resul;} |
      {return eh;}
}

Exp E4(): {Exp exp;} {
    <NOT> exp=E4() {return new Exp.Exp_not(exp);}        |
    <OP_RESTA> exp=E4() {return new Exp.Exp_menos(exp);} |
    exp=E5() {return exp;}
}

Exp E5(): {Exp e6, resul;} {e6=E6() resul=RE5(e6) {return resul;}}
Exp RE5(Exp eh): {Exp e0, resul; Token id;} {
    
    <CAP> e0=E0() <CCI> resul=RE5(new Exp.Exp_ind(eh, e0)) {return resul;}   |
    <PUNTO> id=<ID> resul=RE5(new Exp.Exp_acc(eh, id.image)) {return resul;} |
    <SOMBRERO> resul=RE5(new Exp.Exp_indireccion(eh)) {return resul;}       |
      {return eh;}
}
Exp E6(): {Token token; Exp e0;} {

    token=<LIT_ENTERO> {return new Exp.Exp_entero(token.image);} |
    token=<LIT_DECIMAL> {return new Exp.Exp_real(token.image);}  |
    token=<ID> {return new Exp.Exp_id(token.image);}                |
    token=<LIT_STRING> {return new Exp.Exp_cadena(token.image);} |
    <NULL> {return new Exp.Exp_null();}                          |
    <TRUE> {return new Exp.Exp_bool("true");}                    |
    <FALSE> {return new Exp.Exp_bool("false");}                  |
    <PAP> e0=E0() <PCI> {return e0;}
}

String OP0(): {} {

    <EQ> {return "==";}  |
    <NEQ> {return "!=";} |
    <GT> {return ">";}   |
    <GE> {return ">=";}  |
    <LT> {return "<";}   |
    <LE> {return "<=";}
}

String OP3(): {} {

    <ASTERISCO> {return  "*";}
    <SLASH> {return  "/";}
    <MODULO> {return  "%";}
}

Instrucciones INSTRUCCIONES(): {Instrucciones resul;} {resul=RINSTRUCCIONES(new Instrucciones.No_Instr()) {return resul;}}

Instrucciones RINSTRUCCIONES(Instrucciones insth): {Instruccion inst; Instrucciones resul;}{
    inst=INSTRUCCION() resul=RINSTRUCCIONES(new Instrucciones.Muchas_Instr(insth,inst)){return resul;} |
        {return insth;}

}

Instruccion INSTRUCCION(): {Instruccion instr;} {

    instr=INSTR_COND() {return instr;} |
    instr=INSTR_ASIGNOINVOC() {return instr;} |
    instr=INSTR_WHILE() {return instr;} |
    instr=INSTR_READ() {return instr;} |
    instr=INSTR_WRITE() {return instr;} |
    instr=INSTR_NL() {return instr;} |
    instr=INSTR_NEW() {return instr;} |
    instr=INSTR_DELETE() {return instr;} |
    instr=INSTR_SEQ() {return instr;}


}

Instruccion INSTR_COND(): { Exp e0; Instrucciones instrs; Instruccion resul;} {<IF> e0=E0() <THEN> instrs=INSTRUCCIONES() resul=RINSTR_COND(e0, instrs) {return resul;}}

Instruccion RINSTR_COND(Exp eh, Instrucciones ish): { Instrucciones is; }{
    <END> <PCOMA>{ return new Instruccion.If_then(eh,ish);} |
    <ELSE> is=INSTRUCCIONES() <END> <PCOMA> { return new Instruccion.If_then_else(eh,ish,is);}
}

Instruccion INSTR_ASIGNOINVOC(): {Exp e0; Instruccion resul;} {e0=E0() resul=RINSTR_ASIGNOINVOC(e0){return resul;}}

Instruccion RINSTR_ASIGNOINVOC(Exp eh): {Exp e1; Preales preales;}{
    <PAP> preales=PREALES() <PCI> <PCOMA> {return new Instruccion.Invoc (eh,preales);} |
    <OP_ASIG> e1=E0() <PCOMA> {return new Instruccion.Asignacion(eh,e1);}
}

Instruccion INSTR_WHILE(): { Exp e0; Instrucciones is;} {<WHILE> e0=E0() <DO> is=INSTRUCCIONES() <END> <PCOMA> {return new Instruccion.While (e0,is); }}

Instruccion INSTR_READ(): {Exp e0;} {<READ> e0=E0() <PCOMA> {return new Instruccion.Read(e0);} }

Instruccion INSTR_WRITE(): {Exp e0;} {<WRITE> e0=E0() <PCOMA> {return new Instruccion.Write(e0);} }

Instruccion INSTR_NL(): {} {<NL> <PCOMA> {return new Instruccion.Nl();} }

Instruccion INSTR_NEW(): {Exp e0;} {<NEW> e0=E0() <PCOMA> {return new Instruccion.New(e0);} }

Instruccion INSTR_DELETE(): {Exp e0;} {<DELETE> e0=E0() <PCOMA> {return new Instruccion.Delete(e0);} }

Instruccion INSTR_SEQ(): {Decs decs; Instrucciones is;} {<SEQ> decs=DECS() <BEGIN> is=INSTRUCCIONES() <END> <PCOMA> {return new Instruccion.Mix(decs,is);}}

Preales PREALES(): {Preales resul;} {  resul=RPREALES(new Preales.No_pReal()) {return resul;}}

Preales RPREALES(Preales prealh): {Exp e0; Preales resul;} {
    e0=E0() <COMA> resul=RPREALES(new Preales.Muchos_pReales(prealh, e0)){return resul;} |
        {return prealh;}
}