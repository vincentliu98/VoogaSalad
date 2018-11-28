package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import javafx.scene.layout.AnchorPane;

/**
 * This class handles the addition and editing of category entries.
 *
 * @author Haotian Wang
 */
public class CategoryEditor extends AbstractGameObjectEditor<CategoryClass, CategoryInstance> {
    public CategoryEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param gameObject
     */
    @Override
    public void readGameObjectInstance(CategoryInstance gameObject) {

    }

    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass : The GameObjectClass interface that is being read.
     */
    @Override
    public void readGameObjectClass(CategoryClass gameObjectClass) {

    }
}
