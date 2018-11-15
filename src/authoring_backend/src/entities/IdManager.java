package entities;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.function.Consumer;

public interface IdManager {

    Consumer<SimpleIntegerProperty> requestSetIdFunc();
}
