package entities;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;

public class IdManagerClass implements IdManager {
    Consumer<SimpleIntegerProperty> c;
    private int count;

    IdManagerClass() {
        count = 0;
    }

    @Override
    public Consumer<SimpleIntegerProperty> requestSetIdFunc() {
        // TODO
        c = (n) -> n.setValue(count);
        return c;
    }
}
