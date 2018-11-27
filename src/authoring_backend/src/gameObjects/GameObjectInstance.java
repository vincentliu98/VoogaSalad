package gameObjects;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;

public interface GameObjectInstance {

    ReadOnlyIntegerProperty getInstanceId();

    void setInstanceId(Consumer<SimpleIntegerProperty> setFunc);

    ReadOnlyStringProperty getClassName();

    Consumer<GameObjectInstance> getReturnInstanceIdFunc();


    boolean addProperty(String propertyName, String defaultValue);


    boolean removeProperty(String propertyName);
}
