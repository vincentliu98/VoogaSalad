package gameObjects.tile;

import grids.Point;

import java.util.List;
import java.util.Map;

public class SimpleTileInstance implements TileInstance {
    private String className;
    private String instanceName;
    private int instanceId;
    private Point coord;
    private int height;
    private int width;
    private List<String> imagePathList;
    private String imageSelector;
    private Map<String, String> propertiesMap;
    private TileClass tileClass;


    SimpleTileInstance(
            String className,
            Point topLeftCoord,
            List<String> imagePathList,
            Map<String, String> properties,
            TileClass tileClass) {
        this.className = className;
        this.instanceName = className;
        this.coord = topLeftCoord;
        this.imagePathList = imagePathList;
        this.propertiesMap = properties;
        this.tileClass = tileClass;
        this.height = 1;
        this.width = 1;
        instanceId = 0;
    }


    @Override
    public int getInstanceId() {
        return instanceId;
    }

    @Override
    public void setInstanceId(int newId) {
        instanceId = newId;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String name) {
        className = name;
    }


    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int newWidth) {
        width = newWidth;
    }


    @Override
    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public void setInstanceName(String newInstanceName) {
        instanceName = newInstanceName;
    }

    @Override
    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public void addProperty(String propertyName, String defaultValue) {
        propertiesMap.put(propertyName, defaultValue);
    }

    @Override
    public void removeProperty(String propertyName) {
        propertiesMap.remove(propertyName);
    }

    @Override
    public boolean changePropertyValue(String propertyName, String newValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, newValue);
        return true;
    }

    @Override
    public List<String> getImagePathList() {
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
        } catch (IndexOutOfBoundsException e) {
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
    public Point getCoord() {
        return coord;
    }

    @Override
    public void setCoord(Point coord) {
        this.coord = coord;
    }

    @Override
    public TileClass getGameObjectClass() {
        return tileClass;
    }
}
