package graphUI.groovy;

import groovy.api.Ports;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import utils.ErrorWindow;

import java.util.Map;
import java.util.function.Consumer;

public class DraggableGroovyIconFactory {
    private static final Double ICON_WIDTH = 30.;
    private static final Double ICON_HEIGHT = 30.;

    private Scene myScene;
    private GroovyNodeFactory nodeFactory;
    private DoubleProperty newNodeX, newNodeY;
    private DraggableGroovyIcon draggingIcon;

    public DraggableGroovyIconFactory(
            Scene myScene,
            GroovyNodeFactory nodeFactory,
            DoubleProperty newNodeX,
            DoubleProperty newNodeY
    ) {
        this.myScene = myScene;
        this.nodeFactory = nodeFactory;
        this.newNodeX = newNodeX;
        this.newNodeY = newNodeY;
    }

    public DraggableGroovyIcon gen(Image img, String blockType, boolean fetchArg) {
        return new DraggableGroovyIcon(img, blockType, fetchArg);
    }

    public DraggableGroovyIcon genWithInfo(
            Image img, String blockType, boolean fetchArg, Map<Ports, String> additionalInfo
    ) {
        return new DraggableGroovyIcon(img, blockType, fetchArg, additionalInfo);
    }

    public void dropHandler(DragEvent event, Consumer<GroovyNode> createNode) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()) {
            success = true;
            try {
                String arg = "";
                if (draggingIcon.fetchArg()) {
                    var dialog = new TextInputDialog();
                    dialog.setHeaderText("Type the parameter to initialize " + draggingIcon.blockType());
                    arg = dialog.showAndWait().get();
                }
                createNode.accept(
                        nodeFactory.makeNode(
                                draggingIcon.blockType, draggingIcon.portInfo, newNodeX.get(), newNodeY.get(), arg
                        ).get()
                );
            } catch (Throwable t) {
                t.printStackTrace();
                ErrorWindow.display("Error while creating groovy node", t.toString());
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public class DraggableGroovyIcon extends ImageView {
        private String blockType;
        private boolean fetchArg;
        private Map<Ports, String> portInfo;

        private DraggableGroovyIcon(Image image, String blockType, boolean fetchArg, Map<Ports, String> portInfo) {
            this(image, blockType, fetchArg);
            this.portInfo = portInfo;
        }

        private DraggableGroovyIcon(Image image, String blockType, boolean fetchArg) {
            super(image);
            this.blockType = blockType;
            this.fetchArg = fetchArg;

            setFitWidth(ICON_WIDTH);
            setFitHeight(ICON_HEIGHT);
            setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
            setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));

            setOnDragDetected(event -> {
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(image);
                db.setContent(content);
                draggingIcon = this;
                event.consume();
            });
        }

        public boolean fetchArg() {
            return fetchArg;
        }

        public String blockType() {
            return blockType;
        }
    }
}
