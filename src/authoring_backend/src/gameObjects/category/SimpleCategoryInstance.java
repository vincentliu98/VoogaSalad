package gameObjects.category;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;

public class SimpleCategoryInstance implements CategoryInstance {
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

    @Override
    public void setClassName(String name) {

    }

    /**
     * @return
     */
    @Override
    public Consumer<GameObjectInstance> getReturnInstanceIdFunc() {
        return null;
    }

    /**
     * @param propertyName
     * @param defaultValue
     * @return
     */
    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        return false;
    }

    /**
     * @param propertyName
     * @return
     */
    @Override
    public boolean removeProperty(String propertyName) {
        return false;
    }

    @Override
    public GameObjectType getType() {
        return null;
    }
}
