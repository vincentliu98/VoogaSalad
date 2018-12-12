package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.customEvent.UpdateStatusEventListener;
import gameObjects.crud.GameObjectsCRUDInterface;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.simpleAnimation.SingleNodeFade;

/**
 * This class is the status window when the user clicks on some element on the editing grid. It shows basic stats when the user clicks on some GUI element.
 *
 * @author Haotian Wang
 */
public class StatusView implements SubView<AnchorPane>, UpdateStatusEventListener<Node> {
    private AnchorPane rootPane = new AnchorPane();
    private GameObjectsCRUDInterface objectsManager;
    private VBox modeIndicators;
    private VBox instanceBox;
    private static final double INDICATOR_FADE_TIME = 3000;
    private static final double INITIAL_INDICATOR_FADE_TIME = 15000;

    public StatusView(GameObjectsCRUDInterface manager) {
        objectsManager = manager;
        modeIndicators = new VBox();
        modeIndicators.setSpacing(10);
        Label batchMode = new Label("Batch Mode: Off");
        Label deleteMode = new Label("Deletion Mode: Off");
        batchMode.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTGRAY, null, null)));
        deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        batchMode.setFont(Font.font(20));
        deleteMode.setFont(Font.font(20));
        modeIndicators.getChildren().addAll(batchMode, deleteMode);
        SingleNodeFade.getNodeFadeOut(batchMode, INITIAL_INDICATOR_FADE_TIME).playFromStart();
        SingleNodeFade.getNodeFadeOut(deleteMode, INITIAL_INDICATOR_FADE_TIME).playFromStart();
        instanceBox = new VBox();
        rootPane.getChildren().addAll(modeIndicators, instanceBox);
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

    /**
     * This method specifies what the event listener will do in events occurring.
     *
     * @param view : The parameter to be passed to the EventListener.
     */
    @Override
    public void setOnUpdateStatusEvent(Node view) {
        instanceBox.getChildren().clear();
        instanceBox.getChildren().add(view);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
    }
}
