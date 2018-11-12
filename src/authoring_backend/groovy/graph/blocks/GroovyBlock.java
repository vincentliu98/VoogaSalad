package authoring_backend.groovy.graph.blocks;

import authoring_backend.essentials.GameData;
import authoring_backend.essentials.Replicable;
import authoring_backend.graph.Node;
import authoring_backend.groovy.graph.Ports;

import java.util.List;
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
     * Each GroovyBlock must be transformable into Groovy Code
     * @return Groovy code
     */
    String toGroovy();

    /**
     * Replicates this GroovyBlock
     * @return Deep copy of this GroovyBlock
     */
    T replicate();

    /**
     * Spits out list of possible values given the game data
     * Didn't yet figure out what to spit out though...
     * TODO
     */
    List<?> suggestions(GameData data);

    /**
     * @return Set of ports
     */
    Set<Ports> ports();
}
