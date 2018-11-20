module engine {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    requires java.xml;
    requires java.scripting;
    requires xstream;
    requires org.codehaus.groovy;


    opens gameplay to xstream, org.codehaus.groovy;
    exports gameplay;

}