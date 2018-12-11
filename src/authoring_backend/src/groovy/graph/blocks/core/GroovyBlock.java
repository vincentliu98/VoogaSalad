package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Replicable;
import authoringUtils.frontendUtils.Try;
import graph.Node;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Map;
import java.util.Set;

/**
 * Groovy Block is a composable block elements that represent piece of Groovy code.
 * <p>
 * The type signature GroovyBlock<T extends GroovyBlock> extends Replicable<T>
 * Forces each subclass implementing GroovyBlock<T> to implement Replicable<T>;
 *
 * @author Inchan Hwang
 */

public interface GroovyBlock<T extends GroovyBlock<T>> extends Node, Replicable<T> {
    /**
     * Each GroovyBlock must be transformable into Groovy Code, although
     * it might fail...
     *
     * @return Groovy code
     */
    Try<String> toGroovy(BlockGraph graph);

    /**
     * Replicates this GroovyBlock
     *
     * @return Deep copy of this GroovyBlock
     */
    T replicate();

    /**
     * @return Set of ports
     */
    Set<Ports> ports();

    /**
     * @return Representative Name
     */
    String name();


    /**
     * Params
     */
    Map<String, Object> params();
}
