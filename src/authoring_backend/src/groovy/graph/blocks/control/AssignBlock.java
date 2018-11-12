package groovy.graph.blocks.control;

import essentials.GameData;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

import static groovy.graph.Ports.*;

public class AssignBlock extends SimpleNode implements GroovyBlock<AssignBlock> {
    private GameData gameData;
    private SimpleStringProperty prefix;
    private SimpleStringProperty varName;

    public AssignBlock(String name, GameData gameData) {
        super(name);
        this.gameData = gameData;
        prefix = new SimpleStringProperty("");
        varName = new SimpleStringProperty("");
    }

    /**
     * Should have some convenience methods to provide choices for the prefix
     */
    public SimpleStringProperty prefix() { return prefix; }
    /**
     * Should have some convenience methods to provide choices for the varname when prefix is empty
     */
    public SimpleStringProperty varName() { return varName; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryRHS = graph.findTarget(this, ASSIGN_RHS).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryRHS.flatMap(rhs ->
            tryOut.map(out ->
                prefix.get()+ " " + varName.get() + " = " + rhs
            )
        );
    }

    @Override
    public AssignBlock replicate() { return new AssignBlock(name().get(), gameData); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT, ASSIGN_RHS); }
}
