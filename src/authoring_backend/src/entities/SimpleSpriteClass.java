package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
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
    private ObservableMap<String, BlockGraph> propertiesMap;
    private BlockGraph imageSelector;

    private Function<Integer, Boolean> verifyTileInstanceIdFunc;

    private Function<String, Set<EntityInstance>> getSpriteInstancesFunc;
    private Consumer<EntityInstance> setInstanceIdFunc;
    private Consumer<EntityInstance> returnInstanceIdFunc;
    private Consumer<SpriteInstance> addSpriteInstanceToMapFunc;

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
                      Function<String, Set<EntityInstance>> getSpriteInstancesFunc) {
        this();
        this.verifyTileInstanceIdFunc = verifyTileInstanceIdFunction;
        this.setInstanceIdFunc = setInstanceIdFunc;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
        this.addSpriteInstanceToMapFunc = addSpriteInstanceToMapFunc;
        this.getSpriteInstancesFunc = getSpriteInstancesFunc;
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
    public boolean addProperty(String propertyName, BlockGraph defaultValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            propertiesMap.put(propertyName, defaultValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
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
    public void setImageSelector(BlockGraph blockCode) {
        imageSelector = blockCode;
    }

    @Override
    public BlockGraph getImageSelectorCode() {
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

    @Override
    public Set<EntityInstance> getInstances() {
        return getSpriteInstancesFunc.apply(getClassName().getValue());
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
