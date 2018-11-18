module engine {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires java.xml;
    requires java.scripting;
    requires xstream;
    //requires groovy.all;

    opens gameplay to xstream;
    //opens gameplay to groovy.all;
    exports gameplay;

}