package gameObjects;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleSpriteClass implements SpriteClass {

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

    private Function<String, Set<EntityInstance>> getSpriteInstancesFunc;
    private Consumer<EntityInstance> setInstanceIdFunc;
    private Consumer<EntityInstance> returnInstanceIdFunc;
    private Consumer<SpriteInstance> addSpriteInstanceToMapFunc;
    private Function<Integer, Boolean> deleteSpriteInstanceFromMapFunc;
    private TriConsumer<String, String, String> addSpritePropertyFunc;
    private BiConsumer<String, String> removeSpritePropertyFunc;


    private SimpleSpriteClass() {
        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME);
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        movable = new SimpleBooleanProperty(this, CONST_MOVABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();

    }

    SimpleSpriteClass(Function<Integer, Boolean> verifyTileInstanceIdFunction,
                      Consumer<EntityInstance> setInstanceIdFunc,
                      Consumer<EntityInstance> returnInstanceIdFunc,
                      Consumer<SpriteInstance> addSpriteInstanceToMapFunc,
                      Function<Integer, Boolean> deleteSpriteInstanceFromMapFunc,
                      Function<String, Set<EntityInstance>> getSpriteInstancesFunc,
                      TriConsumer<String, String, String> addSpritePropertyFunc,
                      BiConsumer<String, String> removeSpritePropertyFunc) {
        this();
        this.verifyTileInstanceIdFunc = verifyTileInstanceIdFunction;
        this.setInstanceIdFunc = setInstanceIdFunc;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
        this.addSpriteInstanceToMapFunc = addSpriteInstanceToMapFunc;
        this.deleteSpriteInstanceFromMapFunc = deleteSpriteInstanceFromMapFunc;
        this.getSpriteInstancesFunc = getSpriteInstancesFunc;
        this.addSpritePropertyFunc = addSpritePropertyFunc;
        this.removeSpritePropertyFunc = removeSpritePropertyFunc;
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
        return className.getReadOnlyProperty();
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
            addSpritePropertyFunc.accept(className.getValue(), propertyName, defaultValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        removeSpritePropertyFunc.accept(className.getValue(), propertyName);
        return propertiesMap.remove(propertyName) != null;
    }

    @Override
    public ObservableList getImagePathList() {
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
    public EntityInstance createInstance(int tileId) {
        if (!verifyTileInstanceIdFunc.apply(tileId)) {
            throw new InvalidIdException();
        }
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        propertiesMapCopy.putAll(propertiesMap);
        SpriteInstance spriteInstance = new SimpleSpriteInstance(className.getName(), tileId, propertiesMapCopy, returnInstanceIdFunc);
        setInstanceIdFunc.accept(spriteInstance);
        addSpriteInstanceToMapFunc.accept(spriteInstance);
        return spriteInstance;
    }

    public boolean deleteInstance(int spriteInstanceId) {
        return deleteSpriteInstanceFromMapFunc.apply(spriteInstanceId);
    }

    @Override
    public Set<EntityInstance> getInstances() {
        return getSpriteInstancesFunc.apply(getClassName().get());
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
