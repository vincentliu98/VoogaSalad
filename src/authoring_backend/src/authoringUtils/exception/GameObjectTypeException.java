package authoringUtils.exception;

import gameObjects.gameObject.GameObjectType;

/**
 * @author Yunhao Qing
 */
public class GameObjectTypeException extends Exception {


    public GameObjectTypeException(GameObjectType objectType) {
        super(objectType.name().charAt(0) + objectType.name().substring(1).toLowerCase() +
                "Prototype is not a " + objectType.name().charAt(0)
                + objectType.name().substring(1).toLowerCase());
    }

    public GameObjectTypeException(String className, GameObjectType objectType) {
        super(className +
                " is not a " + objectType.name().charAt(0)
                + objectType.name().substring(1).toLowerCase());
    }

    public GameObjectTypeException(Throwable e) {
        super(e);
    }

    public GameObjectTypeException(String message, Throwable e) {
        super(message, e);
    }
}
