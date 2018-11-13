package groovy.graph.blocks;
import groovy.graph.BlockGraph;
import essentials.Replicable;
import graph.Node;
import groovy.Try;
import groovy.graph.Ports;


import java.util.Set;

/**
 *   Groovy Block is a composable block elements that represent piece of Groovy code.
 *
 *   The subclasses would consist of
 *     a) Controls (For, ForAll, If, ElseIf, Else, Goto, ...)
 *     b) References to entities (GameData, ByIds, i'th item of,
 *              $Clicked, $DraggedFrom, $DraggedTo, $Keydown{KeyEvent}, ...)
 *     c) Entity Creation (CreateById, ...), Removal (DeleteById, DeleteByReference, ...) Block
 *     d) Read/Update property value Block
 *     e) Literals (Strings, Integers, Doubles, ...)
 *     f) Math/Logical blocks
 *     g) Collections (List, Set, Map)
 *     h) User defined Variables ( to drive the syntax such as For(i, 0, 10)  )
 *     i) Custom blocks that will be necessary later on
 *
 *     The type signature GroovyBlock<T extends GroovyBlock> extends Replicable<T>
 *         Forces each subclass implementing GroovyBlock<T> to implement Replicable<T>;
 *
 * @author Inchan Hwang
 */

public interface GroovyBlock<T extends GroovyBlock<T>> extends Node,Replicable<T> {
    /**
     * Each GroovyBlock must be transformable into Groovy Code, although
     * it might fail...
     *
     * @return Groovy code
     */
    Try<String> toGroovy(BlockGraph graph);

    /**
     * Replicates this GroovyBlock
     * @return Deep copy of this GroovyBlock
     */
    T replicate();

    /**
     * @return Set of ports
     */
    Set<Ports> ports();
}
