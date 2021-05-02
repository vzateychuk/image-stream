package vez.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExceptionUtils {

    @FunctionalInterface
    public interface Consumer_WithExceptions<T> {
        void accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface Runnable_WithExceptions {
        void accept() throws Exception;
    }

    //region Public

    /**
     *  Consumer: Converts checked exceptions into runtime exceptions
     * */
    public static <T> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T> consumer) {
        return t -> {
            try { consumer.accept(t); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    /**
     *  Function: Converts checked exceptions into runtime exceptions
     * */
    public static <T, R> Function<T, R> rethrowFunction(Function_WithExceptions<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return null;
            }
        };
    }

    /**
     *  Runnable: Converts checked exceptions into runtime exceptions
     * */
    public static void uncheck(Runnable_WithExceptions t) {
        try { t.accept(); }
        catch (Exception exception) { throwAsUnchecked(exception); }
    }

    //endregion
    //region Private

    @SuppressWarnings ("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E { throw (E)exception; }

    //endregion
}
