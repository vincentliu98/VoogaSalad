package authoringInterface.editor;

import api.SubView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;


/**
 * EditView Class (TabPane > ScrollPane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 */
public class EditView implements SubView<AnchorPane> {
    private AnchorPane anchorPane;
    private final TabPane tabPane = new TabPane();
    //TODO: ScrollViews can be more; need to decide
//    private EntityScrollView entityScrollView;
    private GridScrollView gridScrollView;
    private Button addButton;
    private int index = 1;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the empty window.
     */
    public EditView(){
//        entityScrollView = new EntityScrollView();
        gridScrollView = new GridScrollView();
        initializeAnchorPane();
        addTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        anchorPane.getChildren().addAll(tabPane, addButton);
    }

    /**
     * Set up the anchorPane that will contain the AddButton and TabPane
     */
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
        Tab tempTab = new Tab("Tab" + index);
        tempTab.setClosable(true);
        // need to be changed to make the content different
        tempTab.setContent(gridScrollView.getView());
        tabPane.getTabs().add(tempTab);
        index++;
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return anchorPane;
    }
}
