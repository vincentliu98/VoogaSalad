package authoringInterface.sidebar;

import javafx.scene.image.Image;

import java.util.List;

public class Entity {
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
}
