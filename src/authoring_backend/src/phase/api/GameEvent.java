package phase.api;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class GameEvent {
    public static MouseClick mouseClick() {
        return new MouseClick();
    }

    public static MouseDrag mouseDrag() {
        return new MouseDrag();
    }

    public static KeyPress keyPress(KeyCode code) {
        return new KeyPress(code);
    }

    public abstract boolean matches(Event event);

    public static class MouseClick extends GameEvent {
        @Override
        public boolean matches(Event event) {
            return event.getEventType().equals(MouseEvent.MOUSE_CLICKED);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    public static class MouseDrag extends GameEvent {
        @Override
        public boolean matches(Event event) {
            return false;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    public static class KeyPress extends GameEvent {
        private KeyCode code;

        public KeyPress(KeyCode code) {
            this.code = code;
        }

        @Override
        public boolean matches(Event event) {
            return event.getEventType().equals(KeyEvent.KEY_RELEASED) &&
                    ((KeyEvent) event).getCode() == code;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "." + code.getName();
        }

        public KeyCode getCode() {
            return code;
        }
    }
}
