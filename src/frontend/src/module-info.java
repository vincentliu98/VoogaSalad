module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.scripting;


    opens utils.serializer to xstream;
    opens utils to xstream;

    requires xstream;


    requires authoring_backend;
    requires engine;

    exports authoringInterface;
    exports playingGame;
    exports runningGame;
}