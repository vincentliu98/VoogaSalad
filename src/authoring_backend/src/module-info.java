module authoring_backend {
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;

    opens groovy.graph to xstream;
    opens groovy.api to xstream;
    opens phase.api to xstream;
    opens phase to xstream;

    requires org.codehaus.groovy;
    requires xstream;
}

