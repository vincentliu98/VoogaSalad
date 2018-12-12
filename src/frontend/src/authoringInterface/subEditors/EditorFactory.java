package authoringInterface.subEditors;

import authoringInterface.subEditors.exception.MissingEditorForTypeException;
import authoringUtils.exception.GameObjectTypeException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.gameObject.GameObjectType;
import utils.imageSelector.ImageSelectorController;

/**
 * This Factory class is responsible for creating respective AbstractGameObjectEditors by taking in some arguments.
 *
 * @author Haotian Wang
 */
public class EditorFactory {
    /**
     * This method makes Editors corresponding to a specific type of GameObjectClass or GameObjectInstance.
     *
     * @param gameObjectType:    The GameObjectType that we wants to open an editor for.
     * @param gameObjectManager: The CRUD manager.
     * @return A concrete implementation for AbstractGameObjectEditor.
     * @throws MissingEditorForTypeException
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractGameObjectEditor> T makeEditor(
            GameObjectType gameObjectType,
            GameObjectsCRUDInterface gameObjectManager,
            ImageSelectorController imageSelectorController
    ) throws MissingEditorForTypeException {
        switch (gameObjectType) {
            case ENTITY:
                return (T) new EntityEditor(gameObjectManager, imageSelectorController);
            case PLAYER:
                return (T) new PlayerEditor(gameObjectManager);
            case TILE:
                return (T) new TileEditor(gameObjectManager, imageSelectorController);
            case SOUND:
                return (T) new SoundEditor(gameObjectManager);
//            case SOUND:
//                return (T) new SoundEditor(gameObjectManager);
            case UNSPECIFIED:
                // TODO
                break;
            case CATEGORY:
                return (T) new CategoryEditor(gameObjectManager);
        }
        throw new MissingEditorForTypeException(String.format("An editor is not implemented for the type %s", gameObjectType.toString()));
    }

    /**
     * This is another creation method for the factory, that takes in the String of the GameObjectType rather than the Enum object itself.
     *
     * @param gameObjectType:    A String representation for the GameObjectType.
     * @param gameObjectManager: A CRUD manager for integration with backend.
     * @return An AbstractGameObjectEditor corresponding to the specific type of GameObjects.
     * @throws GameObjectTypeException
     * @throws MissingEditorForTypeException
     */
    public static <T extends AbstractGameObjectEditor> T makeEditor(
            String gameObjectType,
            GameObjectsCRUDInterface gameObjectManager,
            ImageSelectorController imageSelectorController
    ) throws GameObjectTypeException, MissingEditorForTypeException {
        GameObjectType type;
        try {
            type = GameObjectType.valueOf(gameObjectType);
        } catch (IllegalArgumentException e) {
            throw new GameObjectTypeException(String.format("%s is not a valid GameObjectType", gameObjectType), e);
        }
        return makeEditor(type, gameObjectManager, imageSelectorController);
    }
}
