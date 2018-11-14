package entities;

import javafx.beans.property.*;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleTileClass implements TileClass {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty defaultHeight;
    private SimpleIntegerProperty defaultWidth;
    private SimpleBooleanProperty spriteContainable;
    private SimpleListProperty<String> imagePathList;
    private SimpleMapProperty<String, ? extends Object> propertiesMap;
    private SimpleStringProperty imageSelector;

    SimpleTileClass() {

    }




    @Override
    public int getId() {
        return id.
    }


    @Override
    public Object replicate() {
        return null;
    }
}
