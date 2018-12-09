module database {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires java.sql;
    requires java.xml;
    requires java.scripting;

    requires mysql.connector.java;

    exports util;

}