package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IdManager {

    Consumer<SimpleIntegerProperty> requestClassIdFunc();

    Consumer<SimpleIntegerProperty> requestInstanceIdFunc();

    void returnClassIdFunc(Supplier<ReadOnlyIntegerProperty> s);

    void returnInstanceIdFunc(Supplier<ReadOnlyIntegerProperty> s);
}
