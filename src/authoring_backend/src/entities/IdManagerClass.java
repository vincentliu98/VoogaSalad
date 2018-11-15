package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IdManagerClass implements IdManager {
    private Supplier<SimpleIntegerProperty> s;
    private List<Integer> returnedClassIds;
    private List<Integer> returnedInstanceIds;

    private int classCount;
    private int instanceCount;

    IdManagerClass() {
        classCount = 0;
        instanceCount = 0;
        returnedClassIds = new ArrayList<>();
        returnedInstanceIds = new ArrayList<>();
    }

    @Override
    public Consumer<SimpleIntegerProperty> requestClassIdFunc() {
        int id;
        if (!returnedClassIds.isEmpty()) {
            id = returnedClassIds.remove(0);
        }
        else {
            id = classCount;
            classCount++;
        }
        return n -> n.setValue(id);
    }

    @Override
    public Consumer<SimpleIntegerProperty> requestInstanceIdFunc() {
        int id;
        if (!returnedInstanceIds.isEmpty()) {
            id = returnedInstanceIds.remove(0);
        }
        else {
            id = instanceCount;
            instanceCount++;
        }
        return n -> n.setValue(id);
        }

    @Override
    public void returnClassIdFunc(Supplier<ReadOnlyIntegerProperty> s) {
        int returnedId = s.get().getValue();
        returnedClassIds.add(returnedId);
    }

    @Override
    public void returnInstanceIdFunc(Supplier<ReadOnlyIntegerProperty> s) {
        int returnedId = s.get().getValue();
        returnedInstanceIds.add(returnedId);
    }

}
