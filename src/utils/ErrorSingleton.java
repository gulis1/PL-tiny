package utils;

import java.util.Optional;

public class ErrorSingleton {

    private static Optional<String> error = Optional.empty();

    public static Optional<String> getError() {
        return error;
    }

    public static void setError(String err) {

        if (error.isEmpty())
            error = Optional.of(err);

        System.out.println(error.get());
        System.exit(1);
    }
}
