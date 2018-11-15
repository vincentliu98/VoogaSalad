package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.*;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class SimpleTileClass implements TileClass {

    private String CONST_DEFAULTHEIGHT = "defaultHeight";
    private String CONST_DEFAULTWIDTH = "defaultWidth";
    private String CONST_ID = "id";
    private String CONST_SPRITECONTAINABLE = "spriteContainable";
    private String CONST_IMAGEPATHLIST = "imagePathList";
    private String CONST_PROPERTIESMAP = "propertiesMap";

    private ReadOnlyIntegerWrapper id;
    private SimpleIntegerProperty height;
    private SimpleIntegerProperty width;
    private SimpleBooleanProperty spriteContainable;
    private SimpleListProperty<String> imagePathList;
    private SimpleMapProperty<String, Integer> propertiesMap;
    private BlockGraph imageSelector;

    private SimpleTileClass() {
        id = new ReadOnlyIntegerWrapper(this, CONST_ID);
        height = new SimpleIntegerProperty(this, CONST_DEFAULTHEIGHT);
        width = new SimpleIntegerProperty(this, CONST_DEFAULTWIDTH);
        spriteContainable = new SimpleBooleanProperty(this, CONST_SPRITECONTAINABLE);
        imagePathList = new SimpleListProperty<>(this, CONST_IMAGEPATHLIST);
        propertiesMap = new SimpleMapProperty<>(this, CONST_PROPERTIESMAP);
    }

    SimpleTileClass(Consumer<SimpleIntegerProperty> setFunc) {
        super();
        setClassId(setFunc);
    }

    @Override
    public ReadOnlyIntegerProperty getClassId() {
        return id.getReadOnlyProperty();
    }

    @Override
    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(id);
    }

    @Override
    public SimpleMapProperty getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public boolean addProperty(String propertyName, int defaultValue) {
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
    public void setDefaultHeightWidth(int defaultHeight, int defaultWidth) {
        height.setValue(defaultHeight);
        width.setValue(defaultWidth);
    }

    @Override
    public SimpleListProperty getImagePathList() {
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
    public SimpleBooleanProperty isSpriteContainable() {
        return spriteContainable;
    }

    @Override
    public void setSpriteContainable(boolean contains) {
        spriteContainable.setValue(contains);
    }
}
