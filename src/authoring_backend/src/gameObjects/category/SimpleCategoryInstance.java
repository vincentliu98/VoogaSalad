package gameObjects.category;

import gameObjects.entity.EntityClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleCategoryInstance implements CategoryInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;

    private SimpleStringProperty imagePath;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<CategoryClass> getCategoryClassFunc;

    SimpleCategoryInstance(
            String className,
            SimpleStringProperty imagePath,
            ObservableMap<String, String> properties,
            Supplier<CategoryClass> getEntityClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty();
        this.imagePath = imagePath;
        this.propertiesMap = properties;
        this.getCategoryClassFunc = getCategoryClassFunc;
        instanceId = new ReadOnlyIntegerWrapper();
    }

    /**
     * @return
     */
    @Override
    public ReadOnlyIntegerProperty getInstanceId() {
        return instanceId.getReadOnlyProperty();
    }

    /**
     * @param setFunc
     */
    @Override
    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(instanceId);
    }

    /**
     * @return
     */
    @Override
    public ReadOnlyStringProperty getClassName() {
        return className.getReadOnlyProperty();
    }

    /**
     * @param name
     */
    @Override
    public void setClassName(String name) {
        className.setValue(name);
    }

    @Override
    public SimpleStringProperty getInstanceName() {
        return instanceName;
    }

    @Override
    public void setInstanceName(String newInstanceName) {
        instanceName.setValue(newInstanceName);
    }

    /**
     * @param propertyName
     * @param defaultValue
     * @return
     */
    @Override
    public void addProperty(String propertyName, String defaultValue) {
        propertiesMap.put(propertyName, defaultValue);
    }

    /**
     * @param propertyName
     * @return
     */
    @Override
    public void removeProperty(String propertyName) {
        propertiesMap.remove(propertyName);
    }

    @Override
    public boolean changePropertyValue(String propertyName, String newValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, newValue);
        return true;
    }
}
