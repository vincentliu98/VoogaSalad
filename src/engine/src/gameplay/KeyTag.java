package gameplay;

import javafx.scene.input.KeyCode;

public class KeyTag {
    private KeyCode code;

    public KeyTag(KeyCode code) {
        this.code = code;
    }

    public KeyCode code() {
        return code;
    }
}
