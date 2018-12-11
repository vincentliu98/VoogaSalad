package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Map;
import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private String src;

    public RawGroovyBlock(double x, double y, String src) {
        super(x, y);
        this.src = src;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        return Try.success(src);
    }

    @Override
    public RawGroovyBlock replicate() {
        return new RawGroovyBlock(x(), y(), src);
    }

    @Override
    public Set<Ports> ports() {
        return Set.of();
    }

    @Override
    public String name() {
        return src;
    }

    @Override
    public Map<String, Object> params() {
        return Map.of("src", src);
    }
}
