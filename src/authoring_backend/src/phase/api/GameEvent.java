package phase.api;

import javafx.scene.input.KeyCode;

public abstract class GameEvent {
    public String eventType() {
        return this.getClass().getSimpleName();
    }

    public class MouseClick extends GameEvent { }
    public class MouseDrag extends GameEvent { }
    public class KeyPress extends GameEvent {
        private KeyCode code;
        public KeyPress(KeyCode code) { this.code = code; }
        @Override
        public String eventType() {
            return super.eventType()+"."+code; // need to decide what we need from the keycode
        }
    }
}
