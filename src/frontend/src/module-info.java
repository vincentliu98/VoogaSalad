module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.scripting;

    requires authoring_backend;

    exports authoringInterface;
    exports graphUI;
    exports playing;
    exports launchingGame;
}