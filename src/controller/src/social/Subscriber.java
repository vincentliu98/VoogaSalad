package social;

public interface Subscriber {
    void update(EngineEvent engineEvent, Object... args);
}
