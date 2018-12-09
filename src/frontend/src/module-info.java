module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.scripting;

    requires xstream;

    requires authoring_backend;
    requires engine;

    requires org.codehaus.groovy; // should remove later

    exports authoringInterface;
    exports playingGame;
    exports runningGame;
}