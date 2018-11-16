package authoringInterface.sidebar;

import javafx.scene.text.Text;

public class Category implements EditTreeItem<Text> {
    private String entryText;

    public Category(String text) {
        entryText = text;
    }

    /**
     * @return Return a preview of the elements being dragged.
     */
    @Override
    public Text getPreview() {
        Text preview = new Text(entryText);
        preview.setOpacity(0.5);
        return preview;
    }

    /**
     * @return The type of the element being dragged.
     */
    @Override
    public TreeItemType getType() {
        return TreeItemType.CATEGORY;
    }
}
