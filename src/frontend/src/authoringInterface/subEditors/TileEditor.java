package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;

public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    public TileEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(TileInstance userObject) {

    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public TileInstance getObject() {
        return null;
    }

    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass : The GameObjectClass interface that is being read.
     */
    @Override
    public void readGameObjectClass(TileClass gameObjectClass) {

    }

    /**
     * @return The GameObjectClass stored in the internal memory right now.
     */
    @Override
    public TileClass getGameObjectClass() {
        return null;
    }
}
