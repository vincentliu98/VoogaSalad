package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;

public interface EntityInstance {

    ReadOnlyIntegerProperty getInstanceId();

    void setInstanceId(Consumer<SimpleIntegerProperty> setFunc);


}
