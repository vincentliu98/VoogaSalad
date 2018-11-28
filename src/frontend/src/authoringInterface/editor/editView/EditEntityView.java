package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import gameObjects.gameObject.GameObjectInstance;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class EditEntityView implements SubView<ScrollPane>, DraggingCanvas {
    private GridPane entityScrollView;
    private ScrollPane scrollPane;

    public EditEntityView() {
        scrollPane = new ScrollPane();
        entityScrollView = new GridPane();
    }

    @Override
    public void setupDraggingCanvas() {
    }

    @Override
    public ScrollPane getView() {
        return scrollPane;
    }
}
