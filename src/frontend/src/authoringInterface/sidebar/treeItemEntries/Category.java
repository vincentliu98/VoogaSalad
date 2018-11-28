package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.text.Text;

/**
 * This class represents a Category entry that the user can edit on the side view.
 *
 * @author Haotian Wang
 */
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

    /**
     * @return The String name of this edit item entry.
     */
    @Override
    public String getName() {
        return entryText;
    }

    /**
     * Set the name of this item entry.
     *
     * @param name : A new String name for this entry.
     */
    @Override
    public void setName(String name) {
        entryText = name;
    }
}
