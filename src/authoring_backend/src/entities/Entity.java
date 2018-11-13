package entities;

import essentials.Replicable;
import java.awt.geom.Point2D;
import java.util.Map;

public interface Entity extends Replicable {
    int getId();
    Point2D getPosition();
    void setImage(String path);
    void updateImage(String path);
    Map getPropertiesMap();
}
