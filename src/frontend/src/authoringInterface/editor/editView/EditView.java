package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.sidebar.SideViewInterface;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;


/**
 * EditView Class (TabPane > Pane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 */

public class EditView implements SubView<TabPane> {
    private final TabPane tabPane = new TabPane();
    private int index = 1;
    private SideViewInterface sideView;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     * @param sideView
     */
    public EditView(SideViewInterface sideView){
        this.sideView = sideView;
        initializeTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
    }

    private void initializeTab(){
        Tab addTab = new Tab("");
        addTab.getStyleClass().add("addTab");
        addTab.setContent(new AddTabView().getView());
        Button addBtn = new Button("+");
        addBtn.getStyleClass().add("addBtn");
        addTab.setGraphic(addBtn);
        addTab.setClosable(false);
        addBtn.setOnAction(e -> addTabHandler());
        addTab.selectedProperty().addListener((e, o, n) -> {
            tabPane.getSelectionModel().select(1);
        });
        tabPane.getTabs().add(addTab);
        addTabHandler();
    }


    private void constructTab(){
        try {
            Tab tempTab = new Tab("Tab" + index);
            tempTab.setContent(new EditScrollView(sideView).getView());
            tabPane.getTabs().add(tempTab);
        } catch (Exception e) {} //Stay the page if no additional pages
    }

    private void addTabHandler() {
        constructTab();
        index++;
        tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public TabPane getView() {
        return tabPane;
    }
}
