/*
package authoringInterface.editor;

import api.SubView;
import authoringInterface.editor.editView.EditScrollView;
import authoringInterface.sidebar.SideView;
import authoringInterface.sidebar.SideViewInterface;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.layout.AnchorPane;


*/
/**
 * EditView Class (TabPane > ScrollPane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 *//*

public class EditView implements SubView<AnchorPane> {
    private AnchorPane anchorPane;
    private final TabPane tabPane = new TabPane();
    private EditScrollView gridScrollView;
    private Button addButton;
    private int index = 1;

    */
/**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     *//*

    public EditView() {
        gridScrollView = new EditScrollView();
        initializeAnchorPane();
        addTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        anchorPane.getChildren().addAll(tabPane, addButton);
    }

    */
/**
     * Set up the anchorPane that will contain the AddButton and TabPane
     *//*

    private void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        addButton = new Button("+");
        anchorPane.setPadding(Insets.EMPTY);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(addButton, 1.0);
        AnchorPane.setRightAnchor(addButton, 9.0);
        addButton.setOnAction(e -> addTab());
    }

    private void addTab(){
        Tab tempTab = new Tab();
        tempTab.setText("TAB" + index);
        tempTab.setClosable(true);
        // need to be changed to make the content different
        tempTab.setContent(gridScrollView.getView());
        tabPane.getTabs().add(tempTab);
        index++;
    }

    */
/**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     *//*

    @Override
    public AnchorPane getView() {
        return anchorPane;
    }
}
*/
