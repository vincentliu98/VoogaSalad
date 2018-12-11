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
import javafx.util.Pair;
import utils.imageManipulation.ImageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileSettingDialog extends PopUpWindow {
    private GameObjectsCRUDInterface gameObjectManager;
    private Map<Double, TileClass> tileClasses;
    private GenerationMode gMode;
    private ComboBox gModeChoice;
    private List<TileProbPair> pairList;
    private VBox myPane;
    private VBox dialogPane;

    public TileSettingDialog(GameObjectsCRUDInterface gameObjectManager, Stage primaryStage) {
        super(primaryStage);
        this.gameObjectManager = gameObjectManager;
        tileClasses = new HashMap<>();
        pairList = new ArrayList<>();
        myPane = setUpTileBox();

        Button addTile = new Button("Add A Tile");
        addTile.setOnAction(e -> {
            myPane.getChildren().add(addPair());
        });

        gModeChoice = new ComboBox();
        gModeChoice.getItems().addAll("Random", "Repeating");
        gModeChoice.valueProperty().addListener((ov, t, t1) -> {
            if (t1.equals("Random")) gMode = GenerationMode.RANDOM;
            else gMode = GenerationMode.REPEATING;
        });

        dialogPane = new VBox(myPane);
        Button apply = new Button("Apply");
        apply.setOnAction(e -> {
            pairList.forEach(p -> {
                p.setProb(Double.parseDouble(p.probText.getText()));
                tileClasses.put(p.prob, p.tileClass);
            });
        });
        dialogPane.getChildren().addAll(addTile, gModeChoice, apply);
    }

    private TileProbPair addPair() {
        TileProbPair newPair = new TileProbPair();
        pairList.add(newPair);
        return newPair;
    }

    private VBox setUpTileBox() {
        VBox vb = new VBox();
        vb.getChildren().add(addPair());
        return vb;
    }

    public Pair<Map<Double, TileClass>, GenerationMode> retrieveInfo() {
        return new Pair<>(tileClasses, gMode);
    }

    @Override
    public void showWindow() {
        dialog.setScene(new Scene(dialogPane, 500.0, 500.0));
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
        private TextField probText;

        public void setProb(Double prob) {
            this.prob = prob;
        }

        TileProbPair() {
            Pane wrapper = new Pane();
            wrapper.setPrefSize(100.0, 100.0);
            wrapper.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            tileView = new ImageView();
            tileView.setFitHeight(100);
            tileView.setFitWidth(100);
            wrapper.getChildren().add(tileView);
            wrapper.setOnDragOver(e -> setUpHoveringColorDraggedOver(e));
            wrapper.setOnDragDropped(e -> handleDragFromSideView(e));

            Label probLabel = new Label("Probability: ");
            probText = new TextField();

            setPrefSize(100, 100);
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
