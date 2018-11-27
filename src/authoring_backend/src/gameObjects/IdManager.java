package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IdManager {

    Consumer<EntityClass> requestClassIdFunc();

    Consumer<EntityInstance> requestTileInstanceIdFunc();

    Consumer<EntityInstance> requestSpriteInstanceIdFunc();

    Consumer<EntityClass> returnClassIdFunc();

    Consumer<EntityInstance> returnTileInstanceIdFunc();

    Consumer<EntityInstance> returnSpriteInstanceIdFunc();

    Function<Integer, Boolean> verifyClassIdFunc();

    Function<Integer, Boolean> verifyTileInstanceIdFunc();

    Function<Integer, Boolean> verifySpriteInstanceIdFunc();
}
