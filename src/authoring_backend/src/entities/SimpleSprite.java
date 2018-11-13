package entities;

import essentials.Replicable;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleSprite implements Sprite {
    Supplier<Integer> setIdFunction;
    Map propertiesMap;

    public SimpleSprite(Map properties, Point2D position, Supplier<Integer> setIdFunc) {
        propertiesMap = properties;
        setIdFunction = setIdFunc;
        // for now
        propertiesMap.put("position", position);
        properties.put("id", setIdFunction.get());

    }

    @Override
    public int getId() {
        return (int) propertiesMap.get("id");

    }

    @Override
    public Point2D getPosition() {
        return (Point2D) propertiesMap.get("position");
    }

    @Override
    public void setImage(String path) {

        if (propertiesMap.get("id") == null) {
            throw new EntityPropertyException();
        }
        propertiesMap.put("imagePath", path);
    }

    @Override
    public void updateImage(String path) {
        if (propertiesMap.get("id") != null) {
            throw new EntityPropertyException();
        }
        propertiesMap.put("imagePath", path);
    }

    @Override
    public Map getPropertiesMap() {
        return Collections.unmodifiableMap(propertiesMap);
    }

    @Override
    public boolean isMovable() {
        if (propertiesMap.get("movable") == null) {
            throw new EntityPropertyException();
        }
        return (boolean) propertiesMap.get("movable");
    }

    @Override
    public void setMovable(boolean movable) {
        propertiesMap.put("movable", movable);
    }

    protected void setPropertiesMap(Map properties) {
        propertiesMap = properties;
    }
    @Override
    public Replicable replicate() {
        int replicaId = setIdFunction.get();
        Map newMap;
        SimpleSprite newSprite;
        try {
            newMap = propertiesMap.getClass().newInstance();
            newSprite = this.getClass().newInstance();

        }
        catch (Exception e) {
            throw new EntityPropertyException();
        }
        newMap.putAll(propertiesMap);
        newMap.put("id", replicaId);
        newSprite.setPropertiesMap(newMap);
        return newSprite;
    }

}
