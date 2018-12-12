package gameObjects.tile;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;
import groovy.api.BlockGraph;

import java.util.*;
import java.util.function.Function;

public class SimpleTileClass implements TileClass {
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;

    private String className;
    private int classId;
    private boolean entityContainable;
    private int width, height;
    private List<String> imagePathList;
    private Map<String, String> propertiesMap;
    private BlockGraph imageSelector;

    @XStreamOmitField
    private transient TileInstanceFactory myFactory;
    @XStreamOmitField
    private transient ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc;
    @XStreamOmitField
    private transient Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc;
    @XStreamOmitField
    private transient Function<Integer, Boolean> deleteTileInstanceFunc;

    public SimpleTileClass(String name) {
        className = name;
        classId = 0;
        entityContainable = true;
        imagePathList = new ArrayList<>();
        propertiesMap = new HashMap<>();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public SimpleTileClass(
            String className,
            TileInstanceFactory tileInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc,
            Function<Integer, Boolean> deleteTileInstanceFunc) {
        this(className);
        this.myFactory = tileInstanceFactory;
        this.changeTileClassNameFunc = changeTileClassNameFunc;
        this.getAllTileInstancesFunc = getAllTileInstancesFunc;
        this.deleteTileInstanceFunc = deleteTileInstanceFunc;
    }

    @Override
    public int getClassId() {
        return classId;
    }

    @Override
    public void setClassId(int newId) {
        classId = newId;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String newClassName) {
        className = newClassName;
    }

    @Override
    public void changeClassName(String newClassName)
            throws InvalidOperationException {
        changeTileClassNameFunc.accept(className, newClassName);
    }

    @Override
    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        if (propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, defaultValue);
        Collection<TileInstance> tileInstances = getAllInstances();
        for (TileInstance t : tileInstances) {
            t.addProperty(propertyName, defaultValue);
        }
        return true;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.remove(propertyName);
        Collection<TileInstance> tileInstances = getAllInstances();
        for (TileInstance t : tileInstances) {
            t.removeProperty(propertyName);
        }
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
    public BlockGraph getImageSelector() {
        return imageSelector;
    }

    @Override
    public void setImageSelector(BlockGraph graph) {
        this.imageSelector = graph;
    }


    @Override
    public TileInstance createInstance(Point topLeftCoord)
            throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this, topLeftCoord);

    }

    @Override
    public void equipContext(
            TileInstanceFactory tileInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc,
            Function<Integer, Boolean> deleteTileInstanceFunc
    ) {
        this.myFactory = tileInstanceFactory;
        this.changeTileClassNameFunc = changeTileClassNameFunc;
        this.getAllTileInstancesFunc = getAllTileInstancesFunc;
        this.deleteTileInstanceFunc = deleteTileInstanceFunc;
    }

    public boolean deleteInstance(int tileInstanceId) {
        return deleteTileInstanceFunc.apply(tileInstanceId);
    }

    @Override
    public Collection<TileInstance> getAllInstances() {
        Set<TileInstance> s = new HashSet<>();
        Collection<GameObjectInstance> instances = getAllTileInstancesFunc.apply(getClassName());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.TILE) {
                s.add((TileInstance) i);
            }
        }
        return s;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isEntityContainable() {
        return entityContainable;
    }

    @Override
    public void setEntityContainable(boolean contains) {
        entityContainable = contains;
    }
}
