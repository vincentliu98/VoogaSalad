package authoringInterface.editor.editView;

import api.SubView;
import gameObjects.gameObject.GameObjectInstance;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import utils.exception.NodeNotFoundException;

/**
 * This class is responsible for the appearance of the StatusView specific to the Instances in a cell. This InstanceStatusView will have information about the list of Nodes and Instances and provide editing and removing functionalities.
 *
 * @author Haotian Wang
 */
public class InstanceStatusView implements SubView<GridPane> {
    private GridPane gridView;

    public InstanceStatusView(Pane cell) {
        gridView = new GridPane();
        gridView.setGridLinesVisible(true);
        if (cell.getChildrenUnmodifiable().isEmpty()) {
            return;
        }
        gridView.addRow(0, new Label("ID"), new Label("Instance"), new Label("Class"));
        cell.getChildrenUnmodifiable().forEach(node -> {
            GameObjectInstance instance = null;
            try {
                instance = nodeInstanceController.getGameObjectInstance(node);
            } catch (NodeNotFoundException e) {
                // TODO: Proper error handling.
                e.printStackTrace();
            }
            Text instanceID = new Text(instance.getInstanceId().getValue().toString());
            Text instanceName = new Text(instance.getInstanceName().getValue());
            Text className = new Text(instance.getClassName().getValue());
            Button edit = new Button("Edit");
            instanceID.setOnMouseClicked(e -> handleDoubleClick(e, node));
            instanceName.setOnMouseClicked(e -> handleDoubleClick(e, node));
            className.setOnMouseClicked(e -> handleDoubleClick(e, node));
            edit.setOnMouseClicked(e -> handleNodeEditing(node));
            gridView.addRow(
                    gridView.getRowCount(),
                    instanceID,
                    instanceName,
                    className,
                    edit
            );
            listView.getChildrenUnmodifiable().forEach(node1 -> GridPane.setHgrow(node1, Priority.ALWAYS));
        });
    }

    @Override
    public GridPane getView() {
        return gridView;
    }
}
