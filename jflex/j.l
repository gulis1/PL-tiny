package herramientas;
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
  return new UnidadLexica(Token.EOF);
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
{begin}                 {return new UnidadLexica(Token.BEGIN);}
{end}                   {return new UnidadLexica(Token.END);}
{do}                    {return new UnidadLexica(Token.DO);}
{type}                  {return new UnidadLexica(Token.TYPE);}
{var}                   {return new UnidadLexica(Token.VAR);}
{proc}                  {return new UnidadLexica(Token.PROC);}
{if}                    {return new UnidadLexica(Token.IF);}
{then}                  {return new UnidadLexica(Token.THEN);}
{else}                  {return new UnidadLexica(Token.ELSE);}
{while}                 {return new UnidadLexica(Token.WHILE);}
{nl}                    {return new UnidadLexica(Token.NL);}
{read}                  {return new UnidadLexica(Token.READ);}
{write}                 {return new UnidadLexica(Token.WRITE);}
{new}                   {return new UnidadLexica(Token.NEW);}
{seq}                   {return new UnidadLexica(Token.SEQ);}
{string}                {return new UnidadLexica(Token.STRING);}
{array}                 {return new UnidadLexica(Token.ARRAY);}
{record}                {return new UnidadLexica(Token.RECORD);}
{int}                   {return new UnidadLexica(Token.INT);}
{of}                    {return new UnidadLexica(Token.OF);}
{bool}                  {return new UnidadLexica(Token.BOOL);}
{operadorSuma}          {return new UnidadLexica(Token.OP_SUMA);}
{operadorResta}         {return new UnidadLexica(Token.OP_RESTA);}
{operadorModulo}        {return new UnidadLexica(Token.MODULO);}
{operadorAsterisco}     {return new UnidadLexica(Token.ASTERISCO);}
{operadorSlash}         {return new UnidadLexica(Token.SLASH);}
{operadorSombrero}      {return new UnidadLexica(Token.SOMBRERO);}
{operadorPunto}         {return new UnidadLexica(Token.PUNTO);}
{operadorAsign}         {return new UnidadLexica(Token.ASIGN);}
{operadorEq}            {return new UnidadLexica(Token.EQ);}
{operadorNeq}           {return new UnidadLexica(Token.NEQ);}
{operadorGt}            {return new UnidadLexica(Token.GT);}
{operadorGe}            {return new UnidadLexica(Token.GE);}
{operadorLt}            {return new UnidadLexica(Token.LT);}
{operadorLe}            {return new UnidadLexica(Token.LE);}
{and}                   {return new UnidadLexica(Token.AND);}
{not}                   {return new UnidadLexica(Token.NOT);}
{or}                    {return new UnidadLexica(Token.OR);}
{dospuntos}             {return new UnidadLexica(Token.DOSPUNTOS);}
{pap}                   {return new UnidadLexica(Token.PAP);}
{pci}                   {return new UnidadLexica(Token.PCI);}
{cap}                   {return new UnidadLexica(Token.CAP);}
{cci}                   {return new UnidadLexica(Token.CCI);}
{pcoma}                 {return new UnidadLexica(Token.PCOMA);}
{coma}                  {return new UnidadLexica(Token.COMA);}
{litEntero}             {return new UnidadLexica(Token.LIT_ENTERO);}
{litDecimal}            {return new UnidadLexica(Token.LIT_DECIMAL);}
{litString}             {return new UnidadLexica(Token.LIT_STRING);}
{null}                  {return new UnidadLexica(Token.NULL);}
{true}                  {return new UnidadLexica(Token.TRUE);}
{false}                 {return new UnidadLexica(Token.FALSE);}
{identificador}         {return new UnidadLexica(Token.ID);}

