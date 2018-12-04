package gameObjects;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> {
    void accept(T t, U u) throws E;
}

