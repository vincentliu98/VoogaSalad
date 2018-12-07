package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import gameObjects.crud.GameObjectsCRUDInterface;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * This class is the status window when the user clicks on some element on the editing grid. It shows basic stats when the user clicks on some GUI element.
 *
 * @author Haotian Wang
 */
public class StatusView implements SubView<AnchorPane>, UpdateStatusEventListener<Node> {
    private AnchorPane rootPane = new AnchorPane();
    private GameObjectsCRUDInterface objectsManager;

    public StatusView(GameObjectsCRUDInterface manager) { objectsManager = manager; }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return rootPane;
    }

    /**
     * This method specifies what the event listener will do in events occurring.
     *
     * @param view : The parameter to be passed to the EventListener.
     */
    @Override
    public void setOnUpdateStatusEvent(Node view) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(view);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
    }
}
