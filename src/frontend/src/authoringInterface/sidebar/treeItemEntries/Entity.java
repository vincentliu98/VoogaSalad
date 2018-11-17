package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity implements EditTreeItem<ImageView> {
    private Image sprite;
    private int id;
    private String name;
    private static final TreeItemType type = TreeItemType.ENTITY;

    public Entity(Image sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public Entity() {}

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
