package social;

import javafx.scene.layout.Pane;

public interface Icon {
    String getName();
    Pane getView();
    void initBackground();
    void initTitle();
}
