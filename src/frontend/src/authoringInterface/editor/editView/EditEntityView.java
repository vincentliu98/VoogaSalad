package authoringInterface.editor.editView;

import api.DraggingCanvas;
import api.SubView;
import authoringInterface.sidebar.SideViewInterface;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class EditEntityView implements SubView<ScrollPane>, DraggingCanvas {
    private GridPane entityScrollView;
    private ScrollPane scrollPane;
    private SideViewInterface sideView;
    private Map<Node, EditTreeItem> nodeToObjectMap;

    public EditEntityView(SideViewInterface sideView) {
        this.sideView = sideView;
        scrollPane = new ScrollPane();
        nodeToObjectMap = new HashMap<>();
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
