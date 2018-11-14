module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires authoring_backend;

    exports authoringInterface;
}