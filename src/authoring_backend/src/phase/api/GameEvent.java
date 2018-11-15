package phase.api;

import javafx.scene.input.KeyCode;

public abstract class GameEvent {
    public String eventType() {
        return this.getClass().getSimpleName();
    }

    public static MouseClick mouseClick() { return new MouseClick(); }
    public static MouseDrag mouseDrag() { return new MouseDrag(); }
    public static KeyPress keyPress(KeyCode code) { return new KeyPress(code); }

    public static class MouseClick extends GameEvent { }
    public static class MouseDrag extends GameEvent { }
    public static class KeyPress extends GameEvent {
        private KeyCode code;
        public KeyPress(KeyCode code) { this.code = code; }
        @Override
        public String eventType() {
            return super.eventType()+"."+code; // need to decide what we need from the keycode
        }
    }
}
