package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;

public class SimpleSpriteInstance implements SpriteInstance {
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleIntegerProperty tileId;

    SimpleSpriteInstance(int tileId, ) {



    }


    @Override
    public ReadOnlyIntegerProperty getInstanceId() {
        return instanceId.getReadOnlyProperty();
    }

    @Override
    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(instanceId);
    }
}
