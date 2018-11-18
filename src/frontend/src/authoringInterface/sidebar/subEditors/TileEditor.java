package authoringInterface.sidebar.subEditors;

import authoringInterface.sidebar.treeItemEntries.Tile;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// TODO: 11/17/18 Finish TileEditor 
public class TileEditor extends AbstractObjectEditor<Tile> {
    private Tile tile;
    private Double height;
    private Double width;
    private TextField widthField = new TextField();
    private TextField heightField = new TextField();
    private Label widthLabel = new Label("Enter its width: ");
    private Label heighLabel = new Label("Enter its height: ");

    public TileEditor() {
        super();
        tile = new Tile();
        inputText.setText("Your tile name:");
        rootPane.getChildren().addAll(widthLabel, widthField, heighLabel, heightField);

        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Name");
                alert.setContentText("You must give your entity a non-empty name");
                alert.showAndWait();
            } else {
                tile.setName(nameField.getText().trim());
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
                        int id = treeItem.getChildren().size();
                        TreeItem<String> newItem = new TreeItem<>(tile.getName());
                        ImageView preview = new ImageView(tile.getSprite());
                        preview.setFitWidth(50);
                        preview.setFitHeight(50);
                        newItem.setGraphic(preview);
                        tile.setId(id);
                        objectMap.put(newItem.getValue(), tile);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        if (nodeEdited instanceof ImageView) {
                            ((ImageView) nodeEdited).setImage(tile.getSprite());
                        } else if (nodeEdited instanceof Text) {
                            ((Text) nodeEdited).setText(nameField.getText());
                        }
                        break;
                    case EDIT_TREEITEM:
                        break;
                }
            }
        });
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
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(Tile userObject) {
        tile = userObject;
        nameField.setText(tile.getName());
    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public Tile getObject() {
        return tile;
    }
}
