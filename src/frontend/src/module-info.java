module frontend {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires authoring_backend;

    requires org.codehaus.groovy; // should remove later

    exports authoringInterface;
//    exports graphUI;
}