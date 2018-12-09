module controller {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.scripting;

    requires frontend;
    requires engine;
    requires org.twitter4j.core;

    exports launching;
    exports playing;

}