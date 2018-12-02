package gameObjects.sound;

import gameObjects.category.CategoryClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleSoundInstance implements SoundInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;

    private SimpleStringProperty mediaFilePath;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<SoundClass> getSoundClassFunc;

    public SimpleSoundInstance(
            String className,
            SimpleStringProperty mediaFilePath,
            ObservableMap<String, String> properties,
            Supplier<SoundClass> getSoundClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty(className);
        this.mediaFilePath = mediaFilePath;
        this.propertiesMap = properties;
        this.getSoundClassFunc = getSoundClassFunc;
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

    @Override
    public ObservableMap<String, String> getPropertiesMap() { return propertiesMap; }


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

    @Override
    public SimpleStringProperty getMediaFilePath() {
        return mediaFilePath;
    }

    @Override
    public void setMediaFilePath(String newMediaFilePath) {
        mediaFilePath.setValue(newMediaFilePath);
    }

    @Override
    public SoundClass getGameObjectClass() {
        return getSoundClassFunc.get();
    }

}
