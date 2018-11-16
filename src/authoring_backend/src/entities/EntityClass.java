package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface EntityClass {

    void setDefaultHeightWidth(int defaultHeight, int defaultWidth);

    SimpleIntegerProperty getDefaultHeight();

    SimpleIntegerProperty getDefaultWidth();

    ReadOnlyIntegerProperty getClassId();

    void setClassId(Consumer<SimpleIntegerProperty> setFunc);

    Supplier<ReadOnlyIntegerProperty> returnClassId();

    ObservableMap getPropertiesMap();

    boolean addProperty(String propertyName, int defaultValue);

    boolean removeProperty(String propertyName);

    ObservableList getImagePathList();

    void addImagePath(String path);

    boolean removeImagePath(int index);

    void setImageSelector(BlockGraph blockCode);

    BlockGraph getImageSelectorCode();

    EntityInstance createInstance();
}
