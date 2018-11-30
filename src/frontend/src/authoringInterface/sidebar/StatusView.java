package authoringInterface.sidebar;

import api.SubView;
import gameObjects.crud.GameObjectsCRUDInterface;
import javafx.event.Event;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.event.MouseEvent;

/**
 * This class is the status window when the user clicks on some element on the editing grid. It shows basic stats when the user clicks on some GUI element.
 *
 * @author Haotian Wang
 */
public class StatusView implements SubView<AnchorPane> {
    private AnchorPane rootPane = new AnchorPane();
    private GameObjectsCRUDInterface objectsManager;

    public StatusView(GameObjectsCRUDInterface manager) {
        objectsManager = manager;
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return rootPane;
    }
}
