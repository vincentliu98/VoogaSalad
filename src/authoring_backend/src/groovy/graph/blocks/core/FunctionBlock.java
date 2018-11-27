package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import utils.Try;

import java.util.Set;

import static groovy.api.Ports.*;

public class FunctionBlock extends SimpleNode implements GroovyBlock<FunctionBlock> {
    private String op;
    public FunctionBlock(String op) {
        super();
        this.op = op;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A, true).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, B, true).flatMap(b -> b.toGroovy(graph));
        var tryC = graph.findTarget(this, C, true).flatMap(b -> b.toGroovy(graph));
        var tryD = graph.findTarget(this, D, true).flatMap(b -> b.toGroovy(graph));
        var tryE = graph.findTarget(this, E, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap(a ->
            tryB.flatMap(b ->
                tryC.flatMap(c ->
                    tryD.flatMap(d ->
                        tryE.flatMap(e ->
                            tryOut.map(out ->
                                String.format("%s(%s)%s", op, args(a,b,c,d,e),
                                    out.toString().length() > 0 ? ";\n"+out : "")
                            )
                        )
                    )
                )
            )
        );
    }

    private String args(Object... args) {
        var sb = new StringBuilder();
        for(var arg : args) {
            if(arg.toString().isEmpty()) continue;
            if(sb.length() > 0) sb.append(",");
            sb.append(arg);
        } return sb.toString();
    }

    @Override
    public FunctionBlock replicate() { return new FunctionBlock(op); }

    @Override
    public Set<Ports> ports() { return Set.of(A, B, C, D, E, FLOW_OUT); }

    @Override
    public String name() {
        var s = op.split("\\.");
        return s[s.length-1];
    }
}
