package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static groovy.api.Ports.*;

public class FunctionBlock extends SimpleNode implements GroovyBlock<FunctionBlock> {
    private static final int DONT_CARE = -1;

    private String op;
    private Map<Ports, String> portInfo;

    public FunctionBlock(double x, double y, String op, Map<Ports, String> portInfo) {
        super(x, y);
        this.op = op;
        this.portInfo = portInfo;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A, true).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, B, true).flatMap(b -> b.toGroovy(graph));
        var tryC = graph.findTarget(this, C, true).flatMap(b -> b.toGroovy(graph));
        var tryD = graph.findTarget(this, D, true).flatMap(b -> b.toGroovy(graph));
        var tryE = graph.findTarget(this, E, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        int argCount = count(tryA, tryB, tryC, tryD, tryE);
        if (argN() != DONT_CARE && argCount != argN())
            return Try.failure(new ArgNumberMismatchException(argN(), argCount));

        return tryA.flatMap(a ->
                tryB.flatMap(b ->
                        tryC.flatMap(c ->
                                tryD.flatMap(d ->
                                        tryE.flatMap(e ->
                                                tryOut.map(out ->
                                                        String.format("%s(%s)%s", op, args(a, b, c, d, e),
                                                                out.toString().length() > 0 ? ";\n" + out : "")
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private int argN() {
        return portInfo == null ? DONT_CARE : portInfo.size();
    }

    private String args(Object... args) {
        var sb = new StringBuilder();
        for (var arg : args) {
            if (arg.toString().isEmpty()) continue;
            if (sb.length() > 0) sb.append(",");
            sb.append(arg);
        }
        return sb.toString();
    }

    private int count(Try... args) {
        int cnt = 0;
        for (var arg : args) {
            if (!arg.get("").equals("")) cnt++;
        }
        return cnt;
    }

    @Override
    public FunctionBlock replicate() {
        return new FunctionBlock(x(), y(), op, portInfo);
    }

    @Override
    public Set<Ports> ports() {
        var ports = List.of(A, B, C, D, E);
        var ret = new HashSet<Ports>();
        for (int i = 0; i < (argN() == DONT_CARE ? 5 : argN()); i++) ret.add(ports.get(i));
        ret.add(FLOW_OUT);
        return ret;
    }

    @Override
    public String name() {
        return op.replace("GameMethods.", "");
    }

    @Override
    public Map<String, Object> params() {
        return portInfo == null ? Map.of("op", op) : Map.of("op", op, "portInfo", portInfo);
    }
}
