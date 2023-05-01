package ascendente.analisis_lexico;

import ascendente.analisis_sintactico.Token;
%%
%cup
%type UnidadLexica
%unicode
%class Jflexer
%line
%column
%public

%{
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return new UnidadLexica(Token.EOF, null, this.fila(), this.columna());
%eofval}

%init{
%init}

letra = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = 0|({digitoPositivo}{digito}*)
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \@[^\n]*

begin = (B|b)(E|e)(G|g)(I|i)(N|n)
end = (E|e)(N|n)(D|d)
do = (D|d)(O|o)
type = (T|t)(Y|y)(P|p)(E|e)
var = (V|v)(A|a)(R|r)
proc = (P|p)(R|r)(O|o)(C|c)
if = (I|i)(F|f)
then = (T|t)(H|h)(E|e)(N|n)
else = (E|e)(L|l)(S|s)(E|e)
while = (W|w)(H|h)(I|i)(L|l)(E|e)
nl = (N|n)(L|l)
read = (R|r)(E|e)(A|a)(D|d)
write = (W|w)(R|r)(I|i)(T|t)(E|e)
new = (N|n)(E|e)(W|w)
seq = (S|s)(E|e)(q|q)
string = (S|s)(T|t)(R|r)(I|i)(N|n)(G|g)
array = (A|a)(R|r)(R|r)(A|a)(Y|y)
record = (R|r)(E|e)(C|c)(O|o)(R|r)(D|d)
int = (I|i)(N|n)(T|t)
of = (O|o)(F|f)
bool = (B|b)(O|o)(O|o)(L|l)
operadorSuma = \+
operadorResta = \-
operadorModulo = \%
operadorAsterisco = \*
operadorSlash = \/
operadorSombrero = \^
operadorPunto = \.
operadorAsign = \=
operadorEq = \=\=
operadorNeq = \!\=
operadorGt = \>
operadorGe = \>\=
operadorLt = \<
operadorLe = \<\=
and = (A|a)(N|n)(D|d)
not = (N|n)(O|o)(T|t)
or = (O|o)(R|r)
dospuntos = \:
pap = \(
pci = \)
cap = \[
cci = \]
pcoma = \;
coma = \,
litEntero = [\+,\-]?{parteEntera}
litDecimal = [\+,\-]?{parteEntera}\.(0|{parteDecimal})
litString = \'.*\'
null = (N|n)(U|u)(L|l)(L|l)
true = (T|t)(R|r)(U|u)(E|e)
false = (F|f)(A|a)(L|l)(S|s)(E|e)

identificador = {letra}({letra}|{digito}|_)*
%%
{separador}             {}
{comentario}            {}
{begin}                 {return new UnidadLexica(Token.BEGIN, this.lexema(), this.fila(), this.columna());}
{end}                   {return new UnidadLexica(Token.END, this.lexema(), this.fila(), this.columna());}
{do}                    {return new UnidadLexica(Token.DO, this.lexema(), this.fila(), this.columna());}
{type}                  {return new UnidadLexica(Token.TYPE, this.lexema(), this.fila(), this.columna());}
{var}                   {return new UnidadLexica(Token.VAR, this.lexema(), this.fila(), this.columna());}
{proc}                  {return new UnidadLexica(Token.PROC, this.lexema(), this.fila(), this.columna());}
{if}                    {return new UnidadLexica(Token.IF, this.lexema(), this.fila(), this.columna());}
{then}                  {return new UnidadLexica(Token.THEN, this.lexema(), this.fila(), this.columna());}
{else}                  {return new UnidadLexica(Token.ELSE, this.lexema(), this.fila(), this.columna());}
{while}                 {return new UnidadLexica(Token.WHILE, this.lexema(), this.fila(), this.columna());}
{nl}                    {return new UnidadLexica(Token.NL, this.lexema(), this.fila(), this.columna());}
{read}                  {return new UnidadLexica(Token.READ, this.lexema(), this.fila(), this.columna());}
{write}                 {return new UnidadLexica(Token.WRITE, this.lexema(), this.fila(), this.columna());}
{new}                   {return new UnidadLexica(Token.NEW, this.lexema(), this.fila(), this.columna());}
{seq}                   {return new UnidadLexica(Token.SEQ, this.lexema(), this.fila(), this.columna());}
{string}                {return new UnidadLexica(Token.STRING, this.lexema(), this.fila(), this.columna());}
{array}                 {return new UnidadLexica(Token.ARRAY, this.lexema(), this.fila(), this.columna());}
{record}                {return new UnidadLexica(Token.RECORD, this.lexema(), this.fila(), this.columna());}
{int}                   {return new UnidadLexica(Token.INT, this.lexema(), this.fila(), this.columna());}
{of}                    {return new UnidadLexica(Token.OF, this.lexema(), this.fila(), this.columna());}
{bool}                  {return new UnidadLexica(Token.BOOL, this.lexema(), this.fila(), this.columna());}
{operadorSuma}          {return new UnidadLexica(Token.OP_SUMA, this.lexema(), this.fila(), this.columna());}
{operadorResta}         {return new UnidadLexica(Token.OP_RESTA, this.lexema(), this.fila(), this.columna());}
{operadorModulo}        {return new UnidadLexica(Token.MODULO, this.lexema(), this.fila(), this.columna());}
{operadorAsterisco}     {return new UnidadLexica(Token.ASTERISCO, this.lexema(), this.fila(), this.columna());}
{operadorSlash}         {return new UnidadLexica(Token.SLASH, this.lexema(), this.fila(), this.columna());}
{operadorSombrero}      {return new UnidadLexica(Token.SOMBRERO, this.lexema(), this.fila(), this.columna());}
{operadorPunto}         {return new UnidadLexica(Token.PUNTO, this.lexema(), this.fila(), this.columna());}
{operadorAsign}         {return new UnidadLexica(Token.ASIGN, this.lexema(), this.fila(), this.columna());}
{operadorEq}            {return new UnidadLexica(Token.EQ, this.lexema(), this.fila(), this.columna());}
{operadorNeq}           {return new UnidadLexica(Token.NEQ, this.lexema(), this.fila(), this.columna());}
{operadorGt}            {return new UnidadLexica(Token.GT, this.lexema(), this.fila(), this.columna());}
{operadorGe}            {return new UnidadLexica(Token.GE, this.lexema(), this.fila(), this.columna());}
{operadorLt}            {return new UnidadLexica(Token.LT, this.lexema(), this.fila(), this.columna());}
{operadorLe}            {return new UnidadLexica(Token.LE, this.lexema(), this.fila(), this.columna());}
{and}                   {return new UnidadLexica(Token.AND, this.lexema(), this.fila(), this.columna());}
{not}                   {return new UnidadLexica(Token.NOT, this.lexema(), this.fila(), this.columna());}
{or}                    {return new UnidadLexica(Token.OR, this.lexema(), this.fila(), this.columna());}
{dospuntos}             {return new UnidadLexica(Token.DOSPUNTOS, this.lexema(), this.fila(), this.columna());}
{pap}                   {return new UnidadLexica(Token.PAP, this.lexema(), this.fila(), this.columna());}
{pci}                   {return new UnidadLexica(Token.PCI, this.lexema(), this.fila(), this.columna());}
{cap}                   {return new UnidadLexica(Token.CAP, this.lexema(), this.fila(), this.columna());}
{cci}                   {return new UnidadLexica(Token.CCI, this.lexema(), this.fila(), this.columna());}
{pcoma}                 {return new UnidadLexica(Token.PCOMA, this.lexema(), this.fila(), this.columna());}
{coma}                  {return new UnidadLexica(Token.COMA, this.lexema(), this.fila(), this.columna());}
{litEntero}             {return new UnidadLexica(Token.LIT_ENTERO, this.lexema(), this.fila(), this.columna());}
{litDecimal}            {return new UnidadLexica(Token.LIT_DECIMAL, this.lexema(), this.fila(), this.columna());}
{litString}             {return new UnidadLexica(Token.LIT_STRING, this.lexema(), this.fila(), this.columna());}
{null}                  {return new UnidadLexica(Token.NULL, this.lexema(), this.fila(), this.columna());}
{true}                  {return new UnidadLexica(Token.TRUE, this.lexema(), this.fila(), this.columna());}
{false}                 {return new UnidadLexica(Token.FALSE, this.lexema(), this.fila(), this.columna());}
{identificador}         {return new UnidadLexica(Token.ID, this.lexema(), this.fila(), this.columna());}

