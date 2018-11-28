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
     * @param gameObject
     */
    @Override
    public void readGameObjectInstance(TileInstance gameObject) {

    }

    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass : The GameObjectClass interface that is being read.
     */
    @Override
    public void readGameObjectClass(TileClass gameObjectClass) {

    }
}
