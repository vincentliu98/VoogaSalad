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
}
