package authoring_backend.src.essentials;

/**
 * Every Classes implementing Replicable interface should be able to replicate itself.
 * The type signature <R extends Replicable<R>> forces the
 *  class G in Replicable<G> to be a replicable class.
 *
 *
 */
public interface Replicable<R extends Replicable<R>> {
    R replicate();
}
