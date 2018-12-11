module controller {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.scripting;
    requires java.sql;

    requires frontend;
    requires engine;
    requires xstream;
    requires database;
    requires org.twitter4j.core;

    opens social to xstream;
    exports launching;
    exports playing;

}