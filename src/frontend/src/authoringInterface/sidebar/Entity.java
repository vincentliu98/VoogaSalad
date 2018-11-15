package authoringInterface.sidebar;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.List;

public class Entity implements DraggableTreeItem<ImageView> {
    private Image sprite;
    private Integer id;
    private String name;
    private ImageView preview;
    private static final TreeItemType type = TreeItemType.ENTITY;

    public Entity(Image sprite, Integer id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
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
    public ImageView getPreview() {
        ImageView preview = new ImageView(sprite);
        preview.setOpacity(0.5);
        return preview;
    }

    /**
     * @return The type of the element being dragged.
     */
    @Override
    public TreeItemType getType() {
        return type;
    }

    /**
     * Handle the start of dragging action on a DraggableTreeItem.
     *
     * @param canvas
     */
    @Override
    public void handlePressed(Pane canvas, double x, double y) {
        preview = getPreview();
        preview.setX(x);
        preview.setY(y);
        canvas.getChildren().add(preview);
    }

    /**
     * Handle the start of dragging action on a DraggableTreeItem.
     */
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