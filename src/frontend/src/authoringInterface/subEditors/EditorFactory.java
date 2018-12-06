package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.gameObject.GameObjectType;

/**
 * This Factory class is responsible for creating respective AbstractGameObjectEditors by taking in some arguments.
 *
 * @author Haotian Wang
 */
public class EditorFactory {
    /**
     * This method makes Editors corresponding to a specific type of GameObjectClass or GameObjectInstance.
     *
     * @param gameObjectType: The GameObjectType that we wants to open an editor for.
     * @param gameObjectManager: The CRUD manager.
     * @return A concrete implementation for AbstractGameObjectEditor.
     */
    public static AbstractGameObjectEditor makeEditor(GameObjectType gameObjectType, GameObjectsCRUDInterface gameObjectManager) {
        switch (gameObjectType) {
            case ENTITY:
                return new EntityEditor(gameObjectManager);
            case PLAYER:
                // TODO
                break;
            case TILE:
                return new TileEditor(gameObjectManager);
            case SOUND:
                return new SoundEditor(gameObjectManager);
            case UNSPECIFIED:
                // TODO
                break;
            case CATEGORY:
                return new CategoryEditor(gameObjectManager);
        }
        return null;
    }
}
