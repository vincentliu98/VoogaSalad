package authoringUtils.frontendUtils;

/**
 * Every Classes implementing Replicable interface should be able to replicate itself.
 */
public interface Replicable<R> {
    R replicate();
}
