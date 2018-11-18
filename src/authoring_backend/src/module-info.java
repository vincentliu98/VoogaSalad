module authoring_backend {
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;


    opens groovy.graph to xstream;
    opens groovy.api to xstream;
    opens phase.api to xstream;
    opens phase to xstream;
    opens authoring to xstream;
    opens entities to xstream;
    opens grids to xstream;

    requires org.codehaus.groovy;
    requires xstream;

    exports authoring;
    exports phase.api;
    exports groovy.api;
    exports groovy.graph.blocks.core;
    exports entities;
    exports grids;
    exports utils;
}
