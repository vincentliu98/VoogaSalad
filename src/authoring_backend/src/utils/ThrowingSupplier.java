package utils;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}

