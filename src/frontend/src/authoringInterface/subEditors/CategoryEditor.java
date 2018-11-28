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
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return null;
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(CategoryInstance userObject) {

    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public CategoryInstance getObject() {
        return null;
    }

    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass : The GameObjectClass interface that is being read.
     */
    @Override
    public void readGameObjectClass(CategoryClass gameObjectClass) {

    }

    /**
     * @return The GameObjectClass stored in the internal memory right now.
     */
    @Override
    public CategoryClass getGameObjectClass() {
        return null;
    }
}
