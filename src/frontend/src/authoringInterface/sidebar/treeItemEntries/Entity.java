package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * This class represents and Entity object that the user can edit.
 *
 * @author Haotian Wang
 */
public class Entity implements EditTreeItem<ImageView> {
    private static final TreeItemType type = TreeItemType.ENTITY;
    private Image sprite;
    private int id;
    private String name;

    public Entity(Image sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public Entity() {
    }

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
     * @return A Text object containing the name of this entity. This is returned when the sprite variable of the entity is not present.
     */
    public Text getBackupPreview() {
        Text backupPreview = new Text(name);
        return backupPreview;
    }

    /**
     * @return The type of the element being dragged.
     */
    @Override
    public TreeItemType getType() {
        return type;
    }
}
