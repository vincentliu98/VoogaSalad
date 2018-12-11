//package authoringInterface.sidebar.treeItemEntries;
//
//import javafx.scene.media.Media;
//import javafx.scene.text.Text;
//
///**
// * The class that represents a sound file in the side bar.
// *
// * @author Haotian Wang
// */
//public class Sound implements EditTreeItem<Text> {
//    private Media soundfile;
//    private Integer id;
//    private String name;
//    private static final TreeItemType type = TreeItemType.SOUND;
//
//    public Sound(Media file, Integer id, String name) {
//        this.soundfile = file;
//        this.id = id;
//        this.name = name;
//    }
//
//    public Sound(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public Media getSoundfile() {
//        return soundfile;
//    }
//
//    public void setSprite(Media soundfile) {
//        this.soundfile = soundfile;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * @return Return a preview of the elements being dragged.
//     */
//    @Override
//    public Text getPreview() {
//        Text preview = new Text(name);
//        preview.setOpacity(0.5);
//        return new Text(name);
//    }
//
//    /**
//     * @return The type of the element being dragged.
//     */
//    @Override
//    public TreeItemType getType() {
//        return type;
//    }
//}
