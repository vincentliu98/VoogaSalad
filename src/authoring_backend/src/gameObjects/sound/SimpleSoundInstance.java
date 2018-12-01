package gameObjects.sound;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.function.Consumer;

public class SimpleSoundInstance implements SoundInstance {
    /**
     * @return
     */
    @Override
    public ReadOnlyIntegerProperty getInstanceId() {
        return null;
    }

    /**
     * @param setFunc
     */
    @Override
    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {

    }

    /**
     * @return
     */
    @Override
    public ReadOnlyStringProperty getClassName() {
        return null;
    }

    /**
     * @param name
     */
    @Override
    public void setClassName(String name) {

    }

    @Override
    public SimpleStringProperty getInstanceName() {
        return null;
    }

    @Override
    public void setInstanceName(String instanceName) {

    }

    @Override
    public void addProperty(String propertyName, String defaultValue) {

    }

    @Override
    public void removeProperty(String propertyName) {

    }

    @Override
    public boolean changePropertyValue(String propertyName, String newValue) {
        return false;
    }
}
