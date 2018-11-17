package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import authoringInterface.sidebar.treeItemEntries.Entity;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This abstract class provides a boiler plate for different editors because they are pretty similar.
 *
 * @author Haotian Wang
 */
public abstract class AbstractObjectEditor implements SubView<AnchorPane> {
    protected AnchorPane rootPane;
    protected Text inputText;
    protected TextField nameField;
    protected Button confirm;
    protected Button cancel;

    public AbstractObjectEditor() {
        rootPane = new AnchorPane();
        inputText = new Text();
        nameField = new TextField();
        confirm = new Button("Apply");
        cancel = new Button("Cancel");
        rootPane.getChildren().addAll(inputText, nameField, confirm, cancel);
        setupBasicLayout();
    }

    /**
     * This sets up the basic layout for the Abstract Editor.
     */
    private void setupBasicLayout() {
        AnchorPane.setLeftAnchor(inputText, 50.0);
        AnchorPane.setTopAnchor(inputText, 50.0);
        inputText.setLayoutX(14);
        inputText.setLayoutY(27);
        nameField.setLayoutX(208);
        nameField.setLayoutY(37);
        confirm.setLayoutX(296);
        confirm.setLayoutY(436);
        cancel.setLayoutX(391);
        cancel.setLayoutY(436);
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
