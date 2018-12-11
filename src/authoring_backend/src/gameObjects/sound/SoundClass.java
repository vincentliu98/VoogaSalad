package gameObjects.sound;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Haotian Wang
 */
public interface SoundClass extends GameObjectClass {

    SoundInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    String getMediaFilePath();

    void setMediaFilePath(String mediaFilePath);

    double getDuration();

    void setDuration(double newDuration);

    void equipContext(
            SoundInstanceFactory soundInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeSoundClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllSoundInstancesFunc,
            Function<Integer, Boolean> deleteSoundInstanceFunc
    );

    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
