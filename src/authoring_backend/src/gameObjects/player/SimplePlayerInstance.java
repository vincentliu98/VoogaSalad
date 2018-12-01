//package gameObjects.player;
//
//import javafx.beans.property.*;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableMap;
//import gameObjects.gameObject.GameObjectInstance;
//import javafx.collections.ObservableSet;
//
//import java.util.function.Consumer;
//
//public class SimplePlayerInstance implements PlayerInstance {
//    private ReadOnlyStringWrapper className;
//    private ReadOnlyIntegerWrapper instanceId;
//    private SimpleIntegerProperty playerId;
//    private ObservableMap<String, String> propertiesMap;
//    private ObservableSet<Integer> entitiesSet;
//    private Consumer<GameObjectInstance> returnInstanceIdFunc;
//
//
//    SimplePlayerInstance(String className, int playerId,
//                         ObservableMap<String, String> properties,
//                         Consumer<GameObjectInstance> returnInstanceIdFunc
//                         ) {
//        this.className = new ReadOnlyStringWrapper(className);
//        this.playerId = new ReadOnlyIntegerWrapper();
//        this.playerId.setValue(playerId);
//        this.propertiesMap = properties;
//        this.entitiesSet = FXCollections.observableSet();
//        this.returnInstanceIdFunc = returnInstanceIdFunc;
//        instanceId = new ReadOnlyIntegerWrapper();
//    }
//
//    public void setClassName(String name) {
//
//    }
//
//    @Override
//    public ReadOnlyIntegerProperty getInstanceId() {
//        return instanceId.getReadOnlyProperty();
//    }
//
//    @Override
//    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {
//        setFunc.accept(instanceId);
//    }
//
//    @Override
//    public ReadOnlyStringProperty getClassName() { return className; }
//
//    public Consumer<GameObjectInstance> getReturnInstanceIdFunc() {
//        return returnInstanceIdFunc;
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
//    public boolean addEntity(int entityId){
//        if (!entitiesSet.contains(entityId)){
//            entitiesSet.add(entityId);
//            return true;
//        }
//        else return false;
//    }
//
//    public boolean removeEntity(int entityId){
//        return entitiesSet.remove(entityId);
//
//    }
//
//    @Override
//    public boolean removeProperty(String propertyName) {
//        return propertiesMap.remove(propertyName) != null;
//    }
//
//    @Override
//    public SimpleIntegerProperty getPlayerID() { return playerId; }
//}
