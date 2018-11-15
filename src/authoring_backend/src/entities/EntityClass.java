package entities;

import essentials.Replicable;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface EntityClass {

    void setDefaultHeightWidth(int defaultHeight, int defaultWidth);

    int getClassId();

    void setClassId(Consumer<SimpleIntegerProperty> setFunc);

    Map getPropertiesMap();

    boolean addProperty(String propertyName, int defaultValue);

    boolean removeProperty(String propertyName);

    List getImagePathList();

    void addImagePath(String path);

    boolean removeImagePath(int index);

    void setImageSelector(String groovyCode);

    String getImageSelectorCode();

}
