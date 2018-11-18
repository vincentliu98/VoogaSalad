package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Set;
import java.util.function.Consumer;

public interface EntityInstance {

    ReadOnlyIntegerProperty getInstanceId();

    void setInstanceId(Consumer<SimpleIntegerProperty> setFunc);

    ReadOnlyStringProperty getClassName();



}
