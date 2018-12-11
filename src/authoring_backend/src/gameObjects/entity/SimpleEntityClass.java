package gameObjects.entity;

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

public class SimpleEntityClass implements EntityClass {
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_WIDTH = 1;

    private String className;
    private int classId;
    private boolean movable;
    private List<String> imagePathList;
    private Map<String, String> propertiesMap;
    private BlockGraph imageSelector;
    private int width;
    private int height;

    @XStreamOmitField
    private transient EntityInstanceFactory myFactory;
    @XStreamOmitField
    private transient ThrowingBiConsumer<String, String, InvalidOperationException> changeEntityClassNameFunc;
    @XStreamOmitField
    private transient Function<String, Collection<GameObjectInstance>> getAllEntityInstancesFunc;
    @XStreamOmitField
    private transient Function<Integer, Boolean> deleteEntityInstanceFunc;

    public SimpleEntityClass(String className) {
        this.className = className;
        classId = 0;
        movable = true;
        imagePathList = new ArrayList<>();
        propertiesMap = new HashMap<>();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public SimpleEntityClass(
            String className,
            EntityInstanceFactory entityInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeEntityClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllEntityInstancesFunc,
            Function<Integer, Boolean> deleteEntityInstanceFunc
    ) {
        this(className);
        this.myFactory = entityInstanceFactory;
        this.changeEntityClassNameFunc = changeEntityClassNameFunc;
        this.getAllEntityInstancesFunc = getAllEntityInstancesFunc;
        this.deleteEntityInstanceFunc = deleteEntityInstanceFunc;
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
        changeEntityClassNameFunc.accept(className, newClassName);
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
        Collection<EntityInstance> entityInstances = getAllInstances();
        for (EntityInstance e : entityInstances) {
            e.addProperty(propertyName, defaultValue);
        }
        return true;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.remove(propertyName);
        Collection<EntityInstance> entityInstances = getAllInstances();
        for (EntityInstance e : entityInstances) {
            e.removeProperty(propertyName);
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
    public void equipContext(
            EntityInstanceFactory entityInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeEntityClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllEntityInstancesFunc,
            Function<Integer, Boolean> deleteEntityInstanceFunc
    ) {
        myFactory = entityInstanceFactory;
        this.changeEntityClassNameFunc = changeEntityClassNameFunc;
        this.getAllEntityInstancesFunc = getAllEntityInstancesFunc;
        this.deleteEntityInstanceFunc = deleteEntityInstanceFunc;
    }

    @Override
    public EntityInstance createInstance(Point point) throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this, point);
    }

    public boolean deleteInstance(int entityInstanceId) {
        return deleteEntityInstanceFunc.apply(entityInstanceId);
    }


    @Override
    public Collection<EntityInstance> getAllInstances() {
        Set<EntityInstance> s = new HashSet<>();
        Collection<GameObjectInstance> instances = getAllEntityInstancesFunc.apply(getClassName());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.ENTITY) {
                s.add((EntityInstance) i);
            }
        }
        return s;
    }

    @Override
    public boolean isMovable() {
        return movable;
    }

    @Override
    public void setMovable(boolean move) {
        movable = move;
    }
}
