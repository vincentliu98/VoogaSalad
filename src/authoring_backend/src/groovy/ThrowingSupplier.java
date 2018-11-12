package authoring_backend.src.groovy;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}

