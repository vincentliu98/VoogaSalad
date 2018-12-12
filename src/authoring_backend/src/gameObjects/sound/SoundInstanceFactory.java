package gameObjects.sound;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.HashMap;
import java.util.function.Consumer;

public class SoundInstanceFactory {
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;


    public SoundInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public SoundInstance createInstance(SoundClass soundPrototype)
            throws GameObjectTypeException, InvalidIdException {

        if (soundPrototype.getType() != GameObjectType.SOUND) {
            throw new GameObjectTypeException(GameObjectType.SOUND);
        }
        String mediaFilePathCopy = soundPrototype.getMediaFilePath();
        var durationCopy = soundPrototype.getDuration();
        var propertiesMapCopy = new HashMap<>(soundPrototype.getPropertiesMap());
        SoundInstance categoryInstance = new SimpleSoundInstance(soundPrototype.getClassName(), mediaFilePathCopy, propertiesMapCopy, durationCopy, soundPrototype);
        requestInstanceIdFunc.accept(categoryInstance);
        addInstanceToMapFunc.accept(categoryInstance);
        return categoryInstance;

    }
}
