package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class stores the information of a tile in the game authoring engine.
 *
 * @author Haotian Wang
 */
public class Tile implements EditTreeItem<ImageView> {
    private Image sprite;
    private Integer id;
    private String name;

    public Tile(Image sprite, Integer id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public Tile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tile() {
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
        return TreeItemType.TILE;
    }
}
