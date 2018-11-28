package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;

public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    public TileEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }
}
