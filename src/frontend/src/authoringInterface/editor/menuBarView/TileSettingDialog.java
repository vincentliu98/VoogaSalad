package authoringInterface.editor.menuBarView;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tileGeneration.GenerationMode;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.imageManipulation.ImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TileSettingDialog extends PopUpWindow {
    private GameObjectsCRUDInterface gameObjectManager;
    private Map<Double, TileClass> tileClasses;
    private GenerationMode gMode;
    private List<TileProbPair> pairList;
    private DialogPane myPane;

    public TileSettingDialog(GameObjectsCRUDInterface gameObjectManager, Stage primaryStage) {
        super(primaryStage);
        this.gameObjectManager = gameObjectManager;
        pairList = new ArrayList<>();
        myPane = setUpTilePane();
    }

    private DialogPane setUpTilePane() {
        DialogPane myPane = new DialogPane();
        VBox myBox = setUpTileBox();
        myPane.setContent(myBox);
        return myPane;
    }

    private TileProbPair addPair() {
        TileProbPair newPair = new TileProbPair();
        pairList.add(newPair);
        return newPair;
    }

    private VBox setUpTileBox() {
        VBox vb = new VBox();
        vb.getChildren().add(addPair());
        Button apply = new Button("Apply");
        vb.getChildren().add(apply);
        apply.setOnAction(e -> {
            pairList.forEach(p -> {
                tileClasses.put(p.prob, p.tileClass);
                System.out.printf("Now should display " + p.tileClass.getImagePathList() + "with p = " + p.prob);
            });
        });
        return vb;
    }

    @Override
    public void showWindow() {
        dialog.setScene(new Scene(myPane, 500.0, 500.0));
        dialog.showAndWait();
    }

    @Override
    public void closeWindow() {
        dialog.close();
    }

    private class TileProbPair extends HBox {
        private TileClass tileClass;
        private Double prob;
        private ImageView tileView;

        TileProbPair() {
            Pane wrapper = new Pane();
            wrapper.setPrefSize(200.0, 200.0);
            wrapper.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            tileView = new ImageView();
            tileView.setFitHeight(200);
            tileView.setFitWidth(200);
            wrapper.getChildren().add(tileView);
            wrapper.setOnDragOver(e -> setUpHoveringColorDraggedOver(e));
            wrapper.setOnDragDropped(e -> handleDragFromSideView(e));

            Label probLabel = new Label("Probability: ");
            TextField probText = new TextField();
            probText.textProperty().addListener((observable, oldValue, newValue) -> {
                prob = Double.parseDouble(newValue);
            });

            setPrefSize(300, 300);
            getChildren().addAll(wrapper, probLabel, probText);
        }

        private void handleDragFromSideView(DragEvent dragEvent) {
            System.out.println("Drag detected and is dropped");
            dragEvent.acceptTransferModes(TransferMode.ANY);
            try {
                tileClass = gameObjectManager.getGameObjectClass(dragEvent.getDragboard().getString());
                tileView.setImage(ImageManager.getPreview(tileClass));
                System.out.println("drag event image set");
            } catch (Exception e) {
                e.printStackTrace();
            }
            dragEvent.consume();
        }

        /**
         * This method accepts a Region as input and another Paint variable as input to set up a hovering coloring scheme. The region that is inputted will change to the defined color when hovered over.
         *
         * @param dragEvent: A DragEvent which should be DraggedOver
         */
        private void setUpHoveringColorDraggedOver(DragEvent dragEvent) {
            System.out.println("Dragging over");
            dragEvent.acceptTransferModes(TransferMode.ANY);
            if (dragEvent.getGestureSource() instanceof TreeCell) {
                tileView.setOpacity(0.5);
            }
            dragEvent.consume();
        }

    }

}
