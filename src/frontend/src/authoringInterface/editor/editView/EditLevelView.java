package authoringInterface.editor.editView;

public class EditLevelView {
    private static EditLevelView ourInstance = new EditLevelView();

    public static EditLevelView getInstance() {
        return ourInstance;
    }

    private EditLevelView() {
    }
}
