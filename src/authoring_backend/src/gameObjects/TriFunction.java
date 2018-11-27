package gameObjects;

@FunctionalInterface
public interface TriFunction<T, U, V, W> {

    W apply(T t, U u, V v);

}
