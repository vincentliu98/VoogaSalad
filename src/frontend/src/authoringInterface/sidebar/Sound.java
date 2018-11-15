package authoringInterface.sidebar;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.text.Text;

/**
 * The class that represents a sound file in the side bar.
 *
 * @author Haotian Wang
 */
public class Sound implements DraggableTreeItem<Text> {
    private Media soundfile;
    private Integer id;
    private String name;
    private Text preview;
    private static final TreeItemType type = TreeItemType.SOUND;

    public Sound(Media file, Integer id, String name) {
        this.soundfile = file;
        this.id = id;
        this.name = name;
    }

    public Media getSoundfile() {
        return soundfile;
    }

    public void setSprite(Media soundfile) {
        this.soundfile = soundfile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Return a preview of the elements being dragged.
     */
    @Override
    public Text getPreview() {
        Text preview = new Text(name);
        preview.setOpacity(0.5);
        return new Text(name);
    }

    /**
     * @return The type of the element being dragged.
     */
    @Override
    public TreeItemType getType() {
        return TreeItemType.SOUND;
    }

    /**
     * Handle the start of dragging action on a DraggableTreeItem.
     *
     * @param canvas
     * @param x
     * @param y
     */
    @Override
    public void handlePressed(Pane canvas, double x, double y) {
        preview = getPreview();
        preview.setX(x);
        preview.setY(y);
        canvas.getChildren().add(preview);
    }

    @Override
    public void handleDrag(Pane canvas, double x, double y) {
        preview.setX(x);
        preview.setY(y);
    }

    @Override
    public void handleExit(Pane canvas, double x, double y) {
        canvas.getChildren().remove(preview);
    }
}
