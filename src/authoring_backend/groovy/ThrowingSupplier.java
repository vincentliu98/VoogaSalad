package authoring_backend.groovy;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}

