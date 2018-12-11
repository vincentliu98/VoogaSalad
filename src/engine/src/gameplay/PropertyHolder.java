package gameplay;

import java.util.Map;

public abstract class PropertyHolder<X extends PropertyHolder<X>> {
    /**
     * The justification for this Map<String, Object> is that
     * the engine doesn't even have to know what these Objects actually are;
     * xstream and groovy will figure it out on their own by looking inside them
     */
    protected Map<String, Object> props;

    public void set(String key, Object value) {
        props.put(key, value);
    }

    public Object get(String key) {
        return props.get(key);
    }

    public X withProps(String key, Object value) {
        set(key, value);
        return (X) this;
    }
}
