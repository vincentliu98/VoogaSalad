//package authoringInterface;
//
//import api.ParentView;
//import api.SubView;
//
//import authoringInterface.editor.editView.EditView;
//import authoringInterface.sidebar.SideView;
//import javafx.event.ActionEvent;
//import javafx.scene.Group;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCodeCombination;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//
///**
// * This class creates the default window for the authoring engine, built upon the EmptySkeleton.
// *
// * @author Haotian Wang
// */
//
//public class DefaultWindow implements SubView<Parent> {
//    private static final double MENU_BAR_HEIGHT = 30;
//
//    private View emptySkeleton;
//    private MenuBar menuBar;
//    private SideView sideView;
//    private EditView editView;
//
//    public DefaultWindow() {
//        emptySkeleton = new View();
//        menuBar = constructMenuBar(MENU_BAR_HEIGHT);
//        sideView = new SideView();
//        editView = new EditView();
//        setElements();
//        addElements();
//    }
//
//    /**
//     * This method constructs the menu bar.
//     *
//     * @return A MenuBar Node to be displayed at the top of the createPhaseGraph window.
//     */
//    private MenuBar constructMenuBar(double height) {
//
//    }
//
//
//
//    private void setElements() {
//        AnchorPane.setLeftAnchor(menuBar, 0.0);
//        AnchorPane.setRightAnchor(menuBar, 0.0);
//        AnchorPane.setTopAnchor(menuBar, 0.0);
//        AnchorPane.setRightAnchor(sideView.getView(), 0.0);
//        AnchorPane.setTopAnchor(sideView.getView(), 30.0);
//        AnchorPane.setBottomAnchor(sideView.getView(), 0.0);
//        AnchorPane.setLeftAnchor(editView.getView(), 0.0);
//        AnchorPane.setRightAnchor(editView.getView(), 247.9);
//        AnchorPane.setTopAnchor(editView.getView(), 30.0);
//        AnchorPane.setBottomAnchor(editView.getView(), 0.0);
//    }
//
//    private void addElements() {
//        emptySkeleton.getView().getChildren().add(menuBar);
//        emptySkeleton.addChild(sideView);
//        emptySkeleton.addChild(editView);
//    }
//
//    /**
//     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
//     *
//     * @return A "root" JavaFx Node representative of this object.
//     */
//    @Override
//    public Parent getView() {
//        return (Parent) emptySkeleton.getView();
//    }
//}
