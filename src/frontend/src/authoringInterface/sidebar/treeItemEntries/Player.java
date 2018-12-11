package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.text.Text;

public class Player implements EditTreeItem<Text> {
    private static final TreeItemType type = TreeItemType.PLAYER;
    private Integer id;
    private String name;

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Text getPreview() {
        Text preview = new Text(name);
        preview.setOpacity(0.5);
        return new Text(name);
    }

    @Override
    public TreeItemType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
