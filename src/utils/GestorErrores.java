package utils;

import java.util.ArrayList;

public class GestorErrores {

    private static final ArrayList<String> errores = new ArrayList<>();

    public static boolean hayErrores() { return errores.size() > 0; }
    public static void addError(String err) { errores.add(err); }
    public static void printErrores() { errores.forEach(System.out::println); }
}
