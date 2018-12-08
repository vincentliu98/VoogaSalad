package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

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
