package authoring_backend.src.groovy.graph.blocks.control;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.groovy.graph.blocks.GroovyBlock;
import authoring_backend.src.essentials.GameData;
import authoring_backend.src.graph.SimpleNode;
import authoring_backend.src.groovy.Try;
import authoring_backend.src.groovy.graph.Ports;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

import static authoring_backend.src.groovy.graph.Ports.*;

public class AssignBlock extends SimpleNode implements GroovyBlock<AssignBlock> {
    private GameData gameData;
    private SimpleStringProperty prefix;
    private SimpleStringProperty varName;

    public AssignBlock(GameData gameData) {
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
                String.format("%s %s = %s;", prefix.get(), varName.get(), rhs)
            )
        );
    }

    @Override
    public AssignBlock replicate() { return new AssignBlock(gameData); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT, ASSIGN_RHS); }
}
