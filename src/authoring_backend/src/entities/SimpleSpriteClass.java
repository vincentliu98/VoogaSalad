package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleSpriteClass implements SpriteClass {

    private String CONST_DEFAULTHEIGHT = "defaultHeight";
    private String CONST_DEFAULTWIDTH = "defaultWidth";
    private String CONST_ID = "id";
    private String CONST_MOVABLE = "movable";

    private ReadOnlyIntegerWrapper classId;
    private SimpleBooleanProperty movable;
    private ObservableList<String> imagePathList;
    private ObservableMap<String, BlockGraph> propertiesMap;
    private BlockGraph imageSelector;

    Function<Integer, Boolean> verifyTileIdFunc;

    private SimpleSpriteClass() {
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        movable = new SimpleBooleanProperty(this, CONST_MOVABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();

    }

    SimpleSpriteClass(Function<Integer, Boolean> verifyTileInstanceIdFunction,
                      Consumer<EntityInstance> setInstanceIdFunc,
                      Consumer<EntityInstance> returnInstanceIdFunc,
                      Consumer<SpriteInstance> addSpriteInstanceToMapFunc,
                      Consumer<SpriteInstance> removeSpriteInstanceFromMapFunc) {
        this();
        verifyTileIdFunc = verifyTileInstanceIdFunction;
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
        if (!verifyTileIdFunc.apply(tileId)) {
            throw new InvalidIdException();
        }
        EntityInstance spriteInstance = new SimpleSpriteInstance(tileId);

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
