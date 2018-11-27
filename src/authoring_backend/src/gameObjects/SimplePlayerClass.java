//package gameObjects;
//
//import groovy.api.BlockGraph;
//import javafx.beans.property.*;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.ObservableMap;
//import javafx.collections.ObservableSet;
//
//import java.util.Set;
//import java.util.function.Consumer;
//import java.util.function.Function;
//
//public class SimplePlayerClass implements PlayerClass {
//    private String CONST_CLASSNAME = "className";
//    private String CONST_ID = "id";
//
//    private ReadOnlyStringWrapper className;
//    private ReadOnlyIntegerWrapper classId;
//    private ObservableList<String> imagePathList;
//    private ObservableMap<String, String> statsMap;
//    private ObservableSet<Integer> entitySet;
//
//    private Function<Integer, Boolean> verifyEnitityInstanceIdFunc;
//
//    private Function<String, Set<GameObjectInstance>> getPlayerInstancesFunc;
//    private Consumer<GameObjectInstance> setInstanceIdFunc;
//    private Consumer<GameObjectInstance> returnInstanceIdFunc;
//    private Consumer<SpriteInstance> addPlayerInstanceToMapFunc;
//
//    private SimplePlayerClass() {
//        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME);
//        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
//        imagePathList = FXCollections.observableArrayList();
//        statsMap = FXCollections.observableHashMap();
//        entitySet = FXCollections.observableSet();
//
//    }
//
//    SimplePlayerClass(Function<Integer, Boolean> verifyEnitityInstanceIdFunction,
//                      Consumer<GameObjectInstance> setInstanceIdFunc,
//                      Consumer<GameObjectInstance> returnInstanceIdFunc,
//                      Consumer<SpriteInstance> addSpriteInstanceToMapFunc,
//                      Function<String, Set<GameObjectInstance>> getSpriteInstancesFunc) {
//        this();
//        this.verifyEnitityInstanceIdFunc = verifyEnitityInstanceIdFunction;
//        this.setInstanceIdFunc = setInstanceIdFunc;
//        this.returnInstanceIdFunc = returnInstanceIdFunc;
//        this.addSpriteInstanceToMapFunc = addSpriteInstanceToMapFunc;
//        this.getSpriteInstancesFunc = getSpriteInstancesFunc;
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
//    public void setClassName(Consumer<SimpleStringProperty> setFunc) {
//        setFunc.accept(className);
//    }
//
//}
