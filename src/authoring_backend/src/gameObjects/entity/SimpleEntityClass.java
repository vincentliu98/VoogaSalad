package gameObjects.entity;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.TriConsumer;
import gameObjects.exception.InvalidIdException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleEntityClass implements EntityClass {

    private String CONST_CLASSNAME = "className";
    private String CONST_ID = "id";
    private String CONST_MOVABLE = "movable";

    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleBooleanProperty movable;
    private ObservableList<String> imagePathList;
    private ObservableMap<String, String> propertiesMap;
    private String imageSelector;

    private Function<Integer, Boolean> verifyTileInstanceIdFunc;

    private Function<String, Set<GameObjectInstance>> getEntityInstancesFunc;
    private Consumer<GameObjectInstance> setInstanceIdFunc;
    private Consumer<GameObjectInstance> returnInstanceIdFunc;
    private Consumer<EntityInstance> addEntityInstanceToMapFunc;
    private Function<Integer, Boolean> deleteEntityInstanceFromMapFunc;
    private TriConsumer<String, String, String> addEntityPropertyFunc;
    private BiConsumer<String, String> removeEntityPropertyFunc;


    private SimpleEntityClass(String name) {
        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME, name);
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        movable = new SimpleBooleanProperty(this, CONST_MOVABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();
        imageSelector = "";
    }

    SimpleEntityClass(
        String name,
        Function<Integer, Boolean> verifyTileInstanceIdFunction,
        Consumer<GameObjectInstance> setInstanceIdFunc,
        Consumer<GameObjectInstance> returnInstanceIdFunc,
        Consumer<EntityInstance> addEntityInstanceToMapFunc,
        Function<Integer, Boolean> deleteEntityInstanceFromMapFunc,
        Function<String, Set<GameObjectInstance>> getEntityInstancesFunc,
        TriConsumer<String, String, String> addEntityPropertyFunc,
        BiConsumer<String, String> removeEntityPropertyFunc
    ) {
        this(name);
        this.className.set(name);
        this.verifyTileInstanceIdFunc = verifyTileInstanceIdFunction;
        this.setInstanceIdFunc = setInstanceIdFunc;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
        this.addEntityInstanceToMapFunc = addEntityInstanceToMapFunc;
        this.deleteEntityInstanceFromMapFunc = deleteEntityInstanceFromMapFunc;
        this.getEntityInstancesFunc = getEntityInstancesFunc;
        this.addEntityPropertyFunc = addEntityPropertyFunc;
        this.removeEntityPropertyFunc = removeEntityPropertyFunc;
    }


    @Override
    public ReadOnlyIntegerProperty getClassId() {
        return classId.getReadOnlyProperty();
    }

    @Override
    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(classId);
    }

    @Override
    public ReadOnlyStringProperty getClassName() {
        return className;
    }

    @Override
    public void setClassName(Consumer<SimpleStringProperty> setFunc) {
        setFunc.accept(className);
    }

    @Override
    public ObservableMap getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            propertiesMap.put(propertyName, defaultValue);
            addEntityPropertyFunc.accept(className.getValue(), propertyName, defaultValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        removeEntityPropertyFunc.accept(className.getValue(), propertyName);
        return propertiesMap.remove(propertyName) != null;
    }

    @Override
    public ObservableList<String> getImagePathList() {
        return imagePathList;
    }

    @Override
    public void addImagePath(String path) {
        imagePathList.add(path);
    }


    @Override
    public boolean removeImagePath(int index) {
        try {
            imagePathList.remove(index);
            return true;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public void setImageSelector(String blockCode) {
        imageSelector = blockCode;
    }

    @Override
    public String getImageSelectorCode() {
        return imageSelector;
    }

    @Override
    public GameObjectInstance createInstance(int tileId) {
        if (!verifyTileInstanceIdFunc.apply(tileId)) {
            throw new InvalidIdException();
        }
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        propertiesMapCopy.putAll(propertiesMap);
        EntityInstance entityInstance = new SimpleEntityInstance(className.get(), tileId, propertiesMapCopy, returnInstanceIdFunc);
        setInstanceIdFunc.accept(entityInstance);
        addEntityInstanceToMapFunc.accept(entityInstance);
        return entityInstance;
    }

    public boolean deleteInstance(int entityInstanceId) {
        return deleteEntityInstanceFromMapFunc.apply(entityInstanceId);
    }

    @Override
    public Set<GameObjectInstance> getInstances() {
        return getEntityInstancesFunc.apply(getClassName().getValue());
    }

    @Override
    public SimpleBooleanProperty isMovable() {
        return movable;
    }

    @Override
    public void setMovable(boolean move) {
        movable.setValue(move);
    }
}
