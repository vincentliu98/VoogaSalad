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

import java.awt.event.KeyEvent;

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
    private boolean isControlDown;
    private boolean isShiftDown;
    private Label batchMode;
    private Label deleteMode;

    public StatusView(GameObjectsCRUDInterface manager) {
        objectsManager = manager;
        modeIndicators = new VBox();
        modeIndicators.setSpacing(10);
        batchMode = new Label("Batch Mode: Off\nShift to toggle");
        deleteMode = new Label("Deletion Mode: Off\nControl to toggle");
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

    /**
     * Set up a key toggle for toggling for the Shift key.
     *
     * @param keyEvent: A KeyEvent that is the shift key being pressed down.
     */
    private void setUpShift(KeyEvent keyEvent) {
        isShiftDown = !isShiftDown;
        if (isShiftDown) {
            batchMode.setText("Batch Mode: On");
            batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        } else {
            batchMode.setText("Batch Mode: Off");
            batchMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
        SingleNodeFade.getNodeFadeInAndOut(batchMode, INDICATOR_FADE_TIME).playFromStart();
    }

    /**
     * Set up a key toggle and attach the this boolean toggle to some boolean variable of this class.
     *
     * @param keyEvent: A KeyEvent that is the control key being pressed down.
     */
    public void setUpControl(KeyEvent keyEvent) {
        isControlDown = !isControlDown;
        if (isControlDown) {
            deleteMode.setText("Delete Mode: On");
            deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        } else {
            deleteMode.setText("Delete Mode: Off");
            deleteMode.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
        SingleNodeFade.getNodeFadeInAndOut(deleteMode, INDICATOR_FADE_TIME).playFromStart();
    }
}
