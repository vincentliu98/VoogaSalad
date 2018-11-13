package groovy.api;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}

