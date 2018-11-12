package authoring_backend.src.entities;

import java.awt.geom.Point2D;
import java.util.Map;

public interface Entity extends essentials.Replicable {
    int getId();
    Point2D getPosition();
    void setImage(String path);
    void updateImage(String path);
    Map getPropertiesMap();
}
