package ascendente.analisis_lexico;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {

    private final int fila, columna;

    public UnidadLexica(int clase, String lexema, int fila, int columna) {
        super(clase, null);
        this.value = lexema;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public String toString() {

        switch (this.sym) {
            case Token.SEQ: return String.format("SEQ (%d, %d)", this.fila, this.columna);
            case Token.DOSPUNTOS: return String.format("DOSPUNTOS (%d, %d)", this.fila, this.columna);
            case Token.LIT_DECIMAL: return String.format("LIT_DECIMAL (%d, %d)", this.fila, this.columna);
            case Token.PCI: return String.format("PCI (%d, %d)", this.fila, this.columna);
            case Token.GE: return String.format("GE (%d, %d)", this.fila, this.columna);
            case Token.SLASH: return String.format("SLASH (%d, %d)", this.fila, this.columna);
            case Token.CAP: return String.format("CAP (%d, %d)", this.fila, this.columna);
            case Token.INT: return String.format("INT (%d, %d)", this.fila, this.columna);
            case Token.ARRAY: return String.format("ARRAY (%d, %d)", this.fila, this.columna);
            case Token.ASTERISCO: return String.format("ASTERISCO (%d, %d)", this.fila, this.columna);
            case Token.DELETE: return String.format("DELETE (%d, %d)", this.fila, this.columna);
            case Token.WRITE: return String.format("WRITE (%d, %d)", this.fila, this.columna);
            case Token.NOT: return String.format("NOT (%d, %d)", this.fila, this.columna);
            case Token.AND: return String.format("AND (%d, %d)", this.fila, this.columna);
            case Token.LT: return String.format("LT (%d, %d)", this.fila, this.columna);
            case Token.RECORD: return String.format("RECORD (%d, %d)", this.fila, this.columna);
            case Token.TYPE: return String.format("TYPE (%d, %d)", this.fila, this.columna);
            case Token.OR: return String.format("OR (%d, %d)", this.fila, this.columna);
            case Token.BOOL: return String.format("BOOL (%d, %d)", this.fila, this.columna);
            case Token.BEGIN: return String.format("BEGIN (%d, %d)", this.fila, this.columna);
            case Token.IF: return String.format("IF (%d, %d)", this.fila, this.columna);
            case Token.SOMBRERO: return String.format("SOMBRERO (%d, %d)", this.fila, this.columna);
            case Token.ID: return String.format("ID(%s) (%d, %d)", this.value, this.fila, this.columna);
            case Token.LE: return String.format("LE (%d, %d)", this.fila, this.columna);
            case Token.OF: return String.format("OF (%d, %d)", this.fila, this.columna);
            case Token.EOF: return "EOF";
            case Token.TRUE: return String.format("TRUE (%d, %d)", this.fila, this.columna);
            case Token.NEW: return String.format("NEW (%d, %d)", this.fila, this.columna);
            case Token.error: return String.format("error (%d, %d)", this.fila, this.columna);
            case Token.COMA: return String.format("COMA (%d, %d)", this.fila, this.columna);
            case Token.LIT_STRING: return String.format("LIT_STRING (%d, %d)", this.fila, this.columna);
            case Token.MODULO: return String.format("MODULO (%d, %d)", this.fila, this.columna);
            case Token.NULL: return String.format("NULL (%d, %d)", this.fila, this.columna);
            case Token.OP_RESTA: return String.format("OP_RESTA (%d, %d)", this.fila, this.columna);
            case Token.NEQ: return String.format("NEQ (%d, %d)", this.fila, this.columna);
            case Token.CCI: return String.format("CCI (%d, %d)", this.fila, this.columna);
            case Token.EQ: return String.format("EQ (%d, %d)", this.fila, this.columna);
            case Token.PCOMA: return String.format("PCOMA (%d, %d)", this.fila, this.columna);
            case Token.REAL: return String.format("REAL (%d, %d)", this.fila, this.columna);
            case Token.PAP: return String.format("PAP (%d, %d)", this.fila, this.columna);
            case Token.ELSE: return String.format("ELSE (%d, %d)", this.fila, this.columna);
            case Token.PUNTO: return String.format("PUNTO (%d, %d)", this.fila, this.columna);
            case Token.NL: return String.format("NL (%d, %d)", this.fila, this.columna);
            case Token.READ: return String.format("READ (%d, %d)", this.fila, this.columna);
            case Token.WHILE: return String.format("WHILE (%d, %d)", this.fila, this.columna);
            case Token.THEN: return String.format("THEN (%d, %d)", this.fila, this.columna);
            case Token.LIT_ENTERO: return String.format("LIT_ENTERO (%d, %d)", this.fila, this.columna);
            case Token.ASIGN: return String.format("ASIGN (%d, %d)", this.fila, this.columna);
            case Token.END: return String.format("END (%d, %d)", this.fila, this.columna);
            case Token.PROC: return String.format("PROC (%d, %d)", this.fila, this.columna);
            case Token.STRING: return String.format("STRING (%d, %d)", this.fila, this.columna);
            case Token.OP_SUMA: return String.format("OP_SUMA (%d, %d)", this.fila, this.columna);
            case Token.FALSE: return String.format("FALSE (%d, %d)", this.fila, this.columna);
            case Token.GT: return String.format("GT (%d, %d)", this.fila, this.columna);
            case Token.VAR: return String.format("VAR (%d, %d)", this.fila, this.columna);
            case Token.DO: return String.format("DO (%d, %d)", this.fila, this.columna);
            default: throw new IllegalArgumentException("Token no válido.");
        }
    }
}
