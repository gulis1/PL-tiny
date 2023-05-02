import ascendente.analisis_lexico.Jflexer;
import ascendente.analisis_lexico.UnidadLexica;
import ascendente.analisis_sintactico.Parser;
import maquinap.MaquinaP;
import sintaxis_abstracta.*;
import utils.GestorErrores;
import utils.GestorEtiquetado;
import utils.GestorMem;
import utils.TablaSimbolos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Argumentos incorrectos.");
            exit(1);
        }

        try {
            FileReader reader = new FileReader(args[1]);
            switch (args[0]) {

                case "lex":
                    imprimir_tokens(reader);
                    break;
                case "sasc":
                    System.out.println("No sé que hacer aquí.");
                    break;
                case "asc":
                    procesar_asc(reader);
                    break;
                case "sdesc":
                case "desc":
                    System.out.println("No implementado.");
                    break;
                default:
                    System.out.println("Modo desconocido: " + args[0]);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado.");
            exit(1);
        }
    }

    private static void imprimir_tokens(FileReader fileReader) throws IOException {
        System.out.println("Lista de tokens: \n");
        Jflexer jflexer = new Jflexer(fileReader);
        while (true) {
            try {
                UnidadLexica token = jflexer.next_token();
                System.out.println(token + ": " + jflexer.lexema());
                if (token.toString().equals("EOF"))
                    break;
            }
            catch (Error e) {
                System.out.println("Error en: " + jflexer.fila() + ", " + jflexer.columna());
            }
        }
    }

    private static void procesar_asc(FileReader fileReader) {

        Jflexer jflexer = new Jflexer(fileReader);
        Parser parser = new Parser(jflexer);

        try {
            Prog prog = (Prog) parser.parse().value;
            ejecutar(prog);
        }
        catch (Exception e) {
            System.out.println("Error en la construcción del AST:");
            GestorErrores.printErrores();
        }
    }

    private static void ejecutar(Prog programa) {

        TablaSimbolos ts = new TablaSimbolos();
        GestorMem gm = new GestorMem();
        GestorEtiquetado ge = new GestorEtiquetado();
        MaquinaP maquina = new MaquinaP(200, 200 ,400 ,10);

        programa.vincula(ts);
        if (GestorErrores.hayErrores()) {
            System.out.println("Error(es) en proceso de vinculación:");
            GestorErrores.printErrores();
            return;
        }

        programa.tipado();
        if (GestorErrores.hayErrores()) {
            System.out.println("Error(es) en proceso de tipado:");
            GestorErrores.printErrores();
            return;
        }

        programa.asig_espacio(gm);
        if (GestorErrores.hayErrores()) {
            System.out.println("Error(es) en proceso de asignación espacio:");
            GestorErrores.printErrores();
            return;
        }

        programa.etiquetado(ge);
        if (GestorErrores.hayErrores()) {
            System.out.println("Error(es) en proceso de etiquetado:");
            GestorErrores.printErrores();
            return;
        }

        programa.gen_cod(maquina);
        if (GestorErrores.hayErrores()) {
            System.out.println("Error(es) en proceso de generación de código:");
            GestorErrores.printErrores();
            return;
        }

//        maquina.muestraCodigo();
        maquina.ejecuta();
    }
}
