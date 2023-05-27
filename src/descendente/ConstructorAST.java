/* ConstructorAST.java */
/* Generated By:JavaCC: Do not edit this line. ConstructorAST.java */
package descendente;

import sintaxis_abstracta.*;
import utils.Utils;



public class ConstructorAST implements ConstructorASTConstants {

  final public Prog Init() throws ParseException {Prog resul;
    resul = PROG();
    jj_consume_token(0);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Prog PROG() throws ParseException {Decs decs; Instrucciones is;
    decs = DECS();
    jj_consume_token(BEGIN);
    is = INSTRUCCIONES();
    jj_consume_token(END);
    jj_consume_token(PUNTO);
{if ("" != null) return new Prog(decs, is);}
    throw new Error("Missing return statement in function");
}

  final public Tipo TIPO() throws ParseException {Tipo t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case STRING:{
      jj_consume_token(STRING);
{if ("" != null) return new Tipo.Cadena();}
      break;
      }
    case ARRAY:{
      t = TARRAY();
{if ("" != null) return t;}
      break;
      }
    case RECORD:{
      t = TRECORD();
{if ("" != null) return t;}
      break;
      }
    case INT:{
      jj_consume_token(INT);
{if ("" != null) return new Tipo.Entero();}
      break;
      }
    case BOOL:{
      jj_consume_token(BOOL);
{if ("" != null) return new Tipo.Bool();}
      break;
      }
    case ID:{
      t = REF();
{if ("" != null) return t;}
      break;
      }
    case SOMBRERO:{
      t = POINTER();
{if ("" != null) return t;}
      break;
      }
    case REAL:{
      jj_consume_token(REAL);
{if ("" != null) return new Tipo.Real();}
      break;
      }
    case NULL:{
      jj_consume_token(NULL);
{if ("" != null) return new Tipo.Null();}
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Tipo POINTER() throws ParseException {Tipo t;
    jj_consume_token(SOMBRERO);
    t = TIPO();
{if ("" != null) return new Tipo.Pointer(t);}
    throw new Error("Missing return statement in function");
}

  final public Tipo REF() throws ParseException {Token t;
    t = jj_consume_token(ID);
{if ("" != null) return new Tipo.Ref(t.image);}
    throw new Error("Missing return statement in function");
}

  final public Tipo TARRAY() throws ParseException {Token ent; Tipo tipo;
    jj_consume_token(ARRAY);
    jj_consume_token(CAP);
    ent = jj_consume_token(LIT_ENTERO);
    jj_consume_token(CCI);
    jj_consume_token(OF);
    tipo = TIPO();
{if ("" != null) return new Tipo.Array(tipo, ent.image);}
    throw new Error("Missing return statement in function");
}

  final public Tipo TRECORD() throws ParseException {Campos campos;
    jj_consume_token(RECORD);
    campos = CAMPOS();
    jj_consume_token(END);
{if ("" != null) return new  Tipo.Record(campos);}
    throw new Error("Missing return statement in function");
}

  final public Campos CAMPOS() throws ParseException {Campo campo; Campos resul;
    campo = CAMPO();
    resul = RCAMPOS(new Campos.Un_Campo(campo));
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Campos RCAMPOS(Campos campoh) throws ParseException {Campo campo; Campos resul;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      campo = CAMPO();
      resul = RCAMPOS(new Campos.Muchos_Campos(campoh,campo));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[1] = jj_gen;
{if ("" != null) return campoh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Campo CAMPO() throws ParseException {Token id; Tipo tipo;
    id = jj_consume_token(ID);
    jj_consume_token(DOSPUNTOS);
    tipo = TIPO();
    jj_consume_token(PCOMA);
{if ("" != null) return new Campo(id.image, tipo);}
    throw new Error("Missing return statement in function");
}

  final public Decs DECS() throws ParseException {Decs  resul;
    resul = RDECS(new Decs.No_decs());
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Decs RDECS(Decs dech) throws ParseException {Dec dec;Decs resul;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TYPE:
    case VAR:
    case PROC:{
      dec = DEC();
      resul = RDECS(new Decs.Muchas_decs(dech, dec));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[2] = jj_gen;
{if ("" != null) return dech;}
    }
    throw new Error("Missing return statement in function");
}

  final public Dec DEC() throws ParseException {Dec dec;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAR:{
      dec = DEC_VAR();
{if ("" != null) return dec;}
      break;
      }
    case TYPE:{
      dec = DEC_TYPE();
{if ("" != null) return dec;}
      break;
      }
    case PROC:{
      dec = DEC_PROC();
{if ("" != null) return dec;}
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Dec DEC_VAR() throws ParseException {Tipo tipo; Token id;
    jj_consume_token(VAR);
    id = jj_consume_token(ID);
    jj_consume_token(DOSPUNTOS);
    tipo = TIPO();
    jj_consume_token(PCOMA);
{if ("" != null) return new Dec.Dec_var(tipo, id.image);}
    throw new Error("Missing return statement in function");
}

  final public Dec DEC_TYPE() throws ParseException {Tipo tipo; Token id;
    jj_consume_token(TYPE);
    id = jj_consume_token(ID);
    jj_consume_token(DOSPUNTOS);
    tipo = TIPO();
    jj_consume_token(PCOMA);
{if ("" != null) return new  Dec.Dec_type(id.image, tipo);}
    throw new Error("Missing return statement in function");
}

  final public Dec DEC_PROC() throws ParseException {Token id; ParFs parfs; Decs decs; Instrucciones is;
    jj_consume_token(PROC);
    id = jj_consume_token(ID);
    jj_consume_token(PAP);
    parfs = PFORMALES();
    jj_consume_token(PCI);
    decs = DECS();
    jj_consume_token(BEGIN);
    is = INSTRUCCIONES();
    jj_consume_token(END);
    jj_consume_token(PCOMA);
{if ("" != null) return new Dec.Dec_proc(id.image, parfs, decs, is);}
    throw new Error("Missing return statement in function");
}

// ParFs PFORMALES(): {ParFs parfs;} {

//     parfs=MUCHOS_PFORMALES() {return parfs;} |
//     parfs=NO_PARF() {return parfs;}
// }

// ParFs MUCHOS_PFORMALES(): {ParFs parfs; ParF parf;} {
//     parfs=PFORMALES() <COMA> parf=PFORMAL() {return new ParFs.Muchos_ParF(parfs, parf);} |
//     parf=PFNORMAL() { return new Parfs.Muchos_ParF(new Parfs.No_Parfs(),parf);}
// }

// ParFs NO_PARF(): {} { {return new  Parfs.No_Parfs()}}
  final public 

ParFs PFORMALES() throws ParseException {ParFs result; ParF parf;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAR:
    case ID:{
      parf = PFORMAL();
      result = RPFORMALES(new ParFs.Muchos_ParF(new ParFs.No_Parf(),parf));
{if ("" != null) return result;}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      result = RPFORMALES(new ParFs.No_Parf());
{if ("" != null) return result;}
    }
    throw new Error("Missing return statement in function");
}

  final public ParFs RPFORMALES(ParFs parfsh) throws ParseException {ParF parf; ParFs result;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMA:{
      jj_consume_token(COMA);
      parf = PFORMAL();
      result = RPFORMALES(new ParFs.Muchos_ParF(parfsh, parf));
{if ("" != null) return result;}
      break;
      }
    default:
      jj_la1[5] = jj_gen;
{if ("" != null) return parfsh;}
    }
    throw new Error("Missing return statement in function");
}

  final public ParF PFORMAL() throws ParseException {ParF parf;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      parf = PFR_VALOR();
{if ("" != null) return parf;}
      break;
      }
    case VAR:{
      parf = PFR_REF();
{if ("" != null) return parf;}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public ParF PFR_VALOR() throws ParseException {Token id; Tipo tipo;
    id = jj_consume_token(ID);
    jj_consume_token(DOSPUNTOS);
    tipo = TIPO();
{if ("" != null) return new ParF.ParF_Valor(id.image, tipo);}
    throw new Error("Missing return statement in function");
}

  final public ParF PFR_REF() throws ParseException {Token id; Tipo tipo;
    jj_consume_token(VAR);
    id = jj_consume_token(ID);
    jj_consume_token(DOSPUNTOS);
    tipo = TIPO();
{if ("" != null) return new ParF.ParF_Ref(id.image, tipo);}
    throw new Error("Missing return statement in function");
}

  final public Exp E0() throws ParseException {Exp e1, resul;
    e1 = E1();
    resul = RE0(e1);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Exp RE0(Exp eh) throws ParseException {Exp e1; String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:
    case NEQ:
    case GT:
    case GE:
    case LT:
    case LE:{
      op = OP0();
      e1 = E1();
{if ("" != null) return Utils.get_exp_cmp(eh, e1, op);}
      break;
      }
    default:
      jj_la1[7] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E1() throws ParseException {Exp resul, e2, expre1;
    e2 = E2();
    expre1 = RE1(e2);
    resul = RE1P(expre1);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Exp RE1(Exp eh) throws ParseException {Exp e2;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OP_SUMA:{
      jj_consume_token(OP_SUMA);
      e2 = E2();
{if ("" != null) return new Exp.Exp_suma(eh, e2);}
      break;
      }
    default:
      jj_la1[8] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp RE1P(Exp eh) throws ParseException {Exp e1,resul;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OP_RESTA:{
      jj_consume_token(OP_RESTA);
      e1 = E1();
      resul = RE1P(new Exp.Exp_suma(eh, e1));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[9] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E2() throws ParseException {Exp e3, resul;
    e3 = E3();
    resul = RE2(e3);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Exp RE2(Exp eh) throws ParseException {Exp ed;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:{
      jj_consume_token(AND);
      ed = E3();
{if ("" != null) return new Exp.Exp_and(eh, ed);}
      break;
      }
    case OR:{
      jj_consume_token(OR);
      ed = E2();
{if ("" != null) return new Exp.Exp_or(eh, ed);}
      break;
      }
    default:
      jj_la1[10] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E3() throws ParseException {Exp e4, resul;
    e4 = E4();
    resul = RE3(e4);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Exp RE3(Exp eh) throws ParseException {Exp e4, resul; String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASTERISCO:{
      op = OP3();
      e4 = E4();
      resul = RE3(Utils.get_exp_op3(eh, e4, op));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[11] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E4() throws ParseException {Exp exp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      exp = E4();
{if ("" != null) return new Exp.Exp_not(exp);}
      break;
      }
    case OP_RESTA:{
      jj_consume_token(OP_RESTA);
      exp = E4();
{if ("" != null) return new Exp.Exp_menos(exp);}
      break;
      }
    case NULL:
    case TRUE:
    case FALSE:
    case PAP:
    case LIT_ENTERO:
    case LIT_DECIMAL:
    case LIT_STRING:
    case ID:{
      exp = E5();
{if ("" != null) return exp;}
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E5() throws ParseException {Exp e6, resul;
    e6 = E6();
    resul = RE5(e6);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Exp RE5(Exp eh) throws ParseException {Exp e0, resul; Token id;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CAP:{
      jj_consume_token(CAP);
      e0 = E0();
      jj_consume_token(CCI);
      resul = RE5(new Exp.Exp_ind(eh, e0));
{if ("" != null) return resul;}
      break;
      }
    case PUNTO:{
      jj_consume_token(PUNTO);
      id = jj_consume_token(ID);
      resul = RE5(new Exp.Exp_acc(eh, id.image));
{if ("" != null) return resul;}
      break;
      }
    case SOMBRERO:{
      jj_consume_token(SOMBRERO);
      resul = RE5(new Exp.Exp_indireccion(eh));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[13] = jj_gen;
{if ("" != null) return eh;}
    }
    throw new Error("Missing return statement in function");
}

  final public Exp E6() throws ParseException {Token token; Exp e0;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LIT_ENTERO:{
      token = jj_consume_token(LIT_ENTERO);
{if ("" != null) return new Exp.Exp_entero(token.image);}
      break;
      }
    case LIT_DECIMAL:{
      token = jj_consume_token(LIT_DECIMAL);
{if ("" != null) return new Exp.Exp_real(token.image);}
      break;
      }
    case ID:{
      token = jj_consume_token(ID);
{if ("" != null) return new Exp.Exp_id(token.image);}
      break;
      }
    case LIT_STRING:{
      token = jj_consume_token(LIT_STRING);
{if ("" != null) return new Exp.Exp_cadena(token.image);}
      break;
      }
    case NULL:{
      jj_consume_token(NULL);
{if ("" != null) return new Exp.Exp_null();}
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
{if ("" != null) return new Exp.Exp_bool("true");}
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
{if ("" != null) return new Exp.Exp_bool("false");}
      break;
      }
    case PAP:{
      jj_consume_token(PAP);
      e0 = E0();
      jj_consume_token(PCI);
{if ("" != null) return e0;}
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public String OP0() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:{
      jj_consume_token(EQ);
{if ("" != null) return "==";}
      break;
      }
    case NEQ:{
      jj_consume_token(NEQ);
{if ("" != null) return "!=";}
      break;
      }
    case GT:{
      jj_consume_token(GT);
{if ("" != null) return ">";}
      break;
      }
    case GE:{
      jj_consume_token(GE);
{if ("" != null) return ">=";}
      break;
      }
    case LT:{
      jj_consume_token(LT);
{if ("" != null) return "<";}
      break;
      }
    case LE:{
      jj_consume_token(LE);
{if ("" != null) return "<=";}
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public String OP3() throws ParseException {
    jj_consume_token(ASTERISCO);
{if ("" != null) return  "*";}
    jj_consume_token(SLASH);
{if ("" != null) return  "/";}
    jj_consume_token(MODULO);
{if ("" != null) return  "%";}
    throw new Error("Missing return statement in function");
}

  final public Instrucciones INSTRUCCIONES() throws ParseException {Instrucciones resul;
    resul = RINSTRUCCIONES(new Instrucciones.No_Instr());
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Instrucciones RINSTRUCCIONES(Instrucciones insth) throws ParseException {Instruccion inst; Instrucciones resul;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IF:
    case WHILE:
    case NL:
    case READ:
    case WRITE:
    case NEW:
    case DELETE:
    case SEQ:
    case NULL:
    case TRUE:
    case FALSE:
    case NOT:
    case OP_RESTA:
    case PAP:
    case LIT_ENTERO:
    case LIT_DECIMAL:
    case LIT_STRING:
    case ID:{
      inst = INSTRUCCION();
      resul = RINSTRUCCIONES(new Instrucciones.Muchas_Instr(insth,inst));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[16] = jj_gen;
{if ("" != null) return insth;}
    }
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTRUCCION() throws ParseException {Instruccion instr;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IF:{
      instr = INSTR_COND();
{if ("" != null) return instr;}
      break;
      }
    case NULL:
    case TRUE:
    case FALSE:
    case NOT:
    case OP_RESTA:
    case PAP:
    case LIT_ENTERO:
    case LIT_DECIMAL:
    case LIT_STRING:
    case ID:{
      instr = INSTR_ASIGNOINVOC();
{if ("" != null) return instr;}
      break;
      }
    case WHILE:{
      instr = INSTR_WHILE();
{if ("" != null) return instr;}
      break;
      }
    case READ:{
      instr = INSTR_READ();
{if ("" != null) return instr;}
      break;
      }
    case WRITE:{
      instr = INSTR_WRITE();
{if ("" != null) return instr;}
      break;
      }
    case NL:{
      instr = INSTR_NL();
{if ("" != null) return instr;}
      break;
      }
    case NEW:{
      instr = INSTR_NEW();
{if ("" != null) return instr;}
      break;
      }
    case DELETE:{
      instr = INSTR_DELETE();
{if ("" != null) return instr;}
      break;
      }
    case SEQ:{
      instr = INSTR_SEQ();
{if ("" != null) return instr;}
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_COND() throws ParseException {Exp e0; Instrucciones instrs; Instruccion resul;
    jj_consume_token(IF);
    e0 = E0();
    jj_consume_token(THEN);
    instrs = INSTRUCCIONES();
    resul = RINSTR_COND(e0, instrs);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Instruccion RINSTR_COND(Exp eh, Instrucciones ish) throws ParseException {Instrucciones is;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case END:{
      jj_consume_token(END);
      jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.If_then(eh,ish);}
      break;
      }
    case ELSE:{
      jj_consume_token(ELSE);
      is = INSTRUCCIONES();
      jj_consume_token(END);
      jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.If_then_else(eh,ish,is);}
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_ASIGNOINVOC() throws ParseException {Exp e0; Instruccion resul;
    e0 = E0();
    resul = RINSTR_ASIGNOINVOC(e0);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
}

  final public Instruccion RINSTR_ASIGNOINVOC(Exp eh) throws ParseException {Exp e1; Preales preales;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PAP:{
      jj_consume_token(PAP);
      preales = PREALES();
      jj_consume_token(PCI);
      jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Invoc (eh,preales);}
      break;
      }
    case OP_ASIG:{
      jj_consume_token(OP_ASIG);
      e1 = E0();
      jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Asignacion(eh,e1);}
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_WHILE() throws ParseException {Exp e0; Instrucciones is;
    jj_consume_token(WHILE);
    e0 = E0();
    jj_consume_token(DO);
    is = INSTRUCCIONES();
    jj_consume_token(END);
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.While (e0,is);}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_READ() throws ParseException {Exp e0;
    jj_consume_token(READ);
    e0 = E0();
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Read(e0);}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_WRITE() throws ParseException {Exp e0;
    jj_consume_token(WRITE);
    e0 = E0();
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Write(e0);}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_NL() throws ParseException {
    jj_consume_token(NL);
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Nl();}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_NEW() throws ParseException {Exp e0;
    jj_consume_token(NEW);
    e0 = E0();
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.New(e0);}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_DELETE() throws ParseException {Exp e0;
    jj_consume_token(DELETE);
    e0 = E0();
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Delete(e0);}
    throw new Error("Missing return statement in function");
}

  final public Instruccion INSTR_SEQ() throws ParseException {Decs decs; Instrucciones is;
    jj_consume_token(SEQ);
    decs = DECS();
    jj_consume_token(BEGIN);
    is = INSTRUCCIONES();
    jj_consume_token(END);
    jj_consume_token(PCOMA);
{if ("" != null) return new Instruccion.Mix(decs,is);}
    throw new Error("Missing return statement in function");
}

  final public Preales PREALES() throws ParseException {Preales resul;Exp e0;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NULL:
    case TRUE:
    case FALSE:
    case NOT:
    case OP_RESTA:
    case PAP:
    case LIT_ENTERO:
    case LIT_DECIMAL:
    case LIT_STRING:
    case ID:{
      e0 = E0();
      resul = RPREALES(new Preales.Muchos_pReales(new Preales.No_pReal(),e0));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      resul = RPREALES(new Preales.No_pReal());
{if ("" != null) return resul;}
    }
    throw new Error("Missing return statement in function");
}

  final public Preales RPREALES(Preales prealh) throws ParseException {Exp e0; Preales resul;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMA:{
      jj_consume_token(COMA);
      e0 = E0();
      resul = RPREALES(new Preales.Muchos_pReales(prealh, e0));
{if ("" != null) return resul;}
      break;
      }
    default:
      jj_la1[21] = jj_gen;
{if ("" != null) return prealh;}
    }
    throw new Error("Missing return statement in function");
}

  /** Generated Token Manager. */
  public ConstructorASTTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[22];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0xdf000000,0x0,0x3800,0x3800,0x1000,0x0,0x1000,0x0,0x0,0x0,0x0,0x0,0x80000000,0x0,0x80000000,0x0,0x80fe4000,0x80fe4000,0x10200,0x0,0x80000000,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x20000400,0x20000000,0x0,0x0,0x20000000,0x2000000,0x20000000,0x7e000,0x20,0x40,0x14,0x100,0x3c10004b,0x400c00,0x3c100003,0x7e000,0x3c10004b,0x3c10004b,0x0,0x101000,0x3c10004b,0x2000000,};
	}

  /** Constructor with InputStream. */
  public ConstructorAST(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ConstructorAST(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ConstructorASTTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ConstructorAST(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ConstructorASTTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ConstructorASTTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ConstructorAST(ConstructorASTTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ConstructorASTTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 22; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[62];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 22; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 62; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
