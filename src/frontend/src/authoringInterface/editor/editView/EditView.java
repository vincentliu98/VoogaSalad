package authoringInterface.editor.editView;

import api.SubView;
import authoringInterface.sidebar.SideViewInterface;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;


/**
 * EditView Class (TabPane > Pane)
 *      - holding scroll views
 *
 * @author Amy Kim
 * @author jl729
 */

public class EditView implements SubView<TabPane> {
    private final TabPane tabPane = new TabPane();
    private boolean isMyFirstTab;
    private int index = 1;
    private SideViewInterface sideView;

    /**
     * This method constructs the tabView.
     *
     * @return A tabView Node to be displayed at the left side of the createGraph window.
     */
    public EditView(SideViewInterface sideView){
        this.sideView = sideView;
        isMyFirstTab = true;
        initializeTab();
        locateTab();
        tabPane.setTabDragPolicy(TabDragPolicy.REORDER);
    }

    private void initializeTab(){
        Tab addTab = new Tab("");
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
            if (isMyFirstTab) {
                tempTab.setClosable(false);
                isMyFirstTab = false;
            }
            tabPane.getTabs().add(tempTab);
        } catch (Exception e) {} //Stay the page if no additional pages
    }

    /**
     * This function is for only letting the user only access to tabs, not add button Tab.
     * We need this function because currently adding button is as a tab.
     * The user cannot access to it, can only locate withing tabs.
     */
    private void locateTab(){
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            while(c.next()) {
                if(c.wasRemoved()) {
                    if (c.getList().size() == 0) {
                        constructTab();
                    }
                }
            }
        });
    }

    private void addTabHandler() {
        constructTab();
        index ++;
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
