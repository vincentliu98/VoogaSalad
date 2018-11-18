module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires authoring_backend;
    requires engine;

    exports authoringInterface;
    exports graphUI;
}