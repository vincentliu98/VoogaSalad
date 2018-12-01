//package gameObjects.player;
//
//import gameObjects.gameObject.GameObjectInstance;
//import gameObjects.exception.InvalidIdException;
//
//import javafx.beans.property.*;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.ObservableMap;
//
//
//import java.util.Set;
//import java.util.function.Consumer;
//import java.util.function.Function;
//
//public class SimplePlayerClass implements PlayerClass {
//
//    private String CONST_CLASSNAME = "className";
//    private String CONST_ID = "id";
//
//    private ReadOnlyStringWrapper className;
//    private ReadOnlyIntegerWrapper classId;
//    private ObservableList<String> imagePathList;
//    private ObservableMap<String, String> propertiesMap;
//    private String imageSelector;
//
//    private Function<Integer, Boolean> verifyPlayerInstanceIdFunc;
//
//    private Consumer<GameObjectInstance> setInstanceIdFunc;
//    private Consumer<GameObjectInstance> returnInstanceIdFunc;
//    private Function<String, Set<GameObjectInstance>> getPlayerInstancesFunc;
//
//    private Consumer<PlayerInstance> addPlayerInstanceToMapFunc;
//
//    private Function<Integer, Boolean> deletePlayerInstanceFromMapFunc;
//
//    private SimplePlayerClass(String name) {
//        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME, name);
//        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
//        imagePathList = FXCollections.observableArrayList();
//        propertiesMap = FXCollections.observableHashMap();
//        imageSelector = "";
//    }
//
//    SimplePlayerClass(String name,
//                      Function<Integer, Boolean> verifyPlayerInstanceIdFunc,
//                      Consumer<GameObjectInstance> setInstanceIdFunc,
//                      Consumer<GameObjectInstance> returnInstanceIdFunc,
//                      Consumer<PlayerInstance> addPlayerInstanceToMapFunc,
//                      Function<Integer, Boolean> deletePlayerInstanceFromMapFunc,
//                      Function<String, Set<GameObjectInstance>> getPlayerInstancesFunc) {
//        this(name);
//        this.verifyPlayerInstanceIdFunc = verifyPlayerInstanceIdFunc;
//        this.setInstanceIdFunc = setInstanceIdFunc;
//        this.returnInstanceIdFunc = returnInstanceIdFunc;
//        this.addPlayerInstanceToMapFunc = addPlayerInstanceToMapFunc;
//        this.deletePlayerInstanceFromMapFunc = deletePlayerInstanceFromMapFunc;
//        this.getPlayerInstancesFunc = getPlayerInstancesFunc;
//    }
//
//    @Override
//    public ReadOnlyIntegerProperty getClassId() {
//        return classId.getReadOnlyProperty();
//    }
//
//    @Override
//    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {
//        setFunc.accept(classId);
//    }
//
//    @Override
//    public ReadOnlyStringProperty getClassName() {
//        return className.getReadOnlyProperty();
//    }
//
//    @Override
//    public void changeClassName(String newClassName) {
//
//    }
//
//    @Override
//    public void setClassName(String newClassName) {
//
//    }
//
//    @Override
//    public void setClassName(Consumer<SimpleStringProperty> setFunc) {
//        setFunc.accept(className);
//    }
//
//    @Override
//    public ObservableMap getPropertiesMap() {
//        return propertiesMap;
//    }
//
//
//    @Override
//    public ObservableList getImagePathList() {
//        return imagePathList;
//    }
//
//    @Override
//    public void addImagePath(String path) {
//        imagePathList.add(path);
//    }
//
//
//    @Override
//    public boolean removeImagePath(int index) {
//        try {
//            imagePathList.remove(index);
//            return true;
//        }
//        catch (IndexOutOfBoundsException e) {
//            return false;
//        }
//    }
//
//    @Override
//    public void setImageSelector(String blockCode) {
//        imageSelector = blockCode;
//    }
//
//    @Override
//    public String getImageSelectorCode() {
//        return imageSelector;
//    }
//
//    @Override
//    public PlayerInstance createInstance(int playerId) {
//        if (!verifyPlayerInstanceIdFunc.apply(playerId)) {
//            throw new InvalidIdException();
//        }
//        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
//        propertiesMapCopy.putAll(propertiesMap);
//        PlayerInstance playerInstance = new SimplePlayerInstance(className.get(), playerId, propertiesMapCopy, returnInstanceIdFunc);
//        setInstanceIdFunc.accept(playerInstance);
//        addPlayerInstanceToMapFunc.accept(playerInstance);
//        return playerInstance;
//
//    }
//
//    public boolean deleteInstance(int playerInstanceId) {
//        return deletePlayerInstanceFromMapFunc.apply(playerInstanceId);
//    }
//
//    @Override
//    public Set<GameObjectInstance> getAllInstances() {
//        return getPlayerInstancesFunc.apply(getClassName().getValue());
//    }
//
//    @Override
//    public boolean addProperty(String propertyName, String defaultValue) {
//        if (!propertiesMap.containsKey(propertyName)) {
//            propertiesMap.put(propertyName, defaultValue);
//            return true;
//        }
//        return false;
//    }
//
//
//    @Override
//    public boolean removeProperty(String propertyName) {
//        return propertiesMap.remove(propertyName) != null;
//    }
//
//    @Override
//    public PlayerInstance createInstance() {
//        return null;
//    }
//}
