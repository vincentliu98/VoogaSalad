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
        return null;
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
     */
    @Override
    public void handleDrag(Pane canvas) {
        canvas.setOnDragDetected(e -> canvas.startFullDrag());
        canvas.setOnMousePressed();
    }
}
