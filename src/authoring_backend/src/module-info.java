module authoring_backend {
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;

    opens groovy.graph to xstream;
    opens groovy.api to xstream;
    opens phase.api to xstream;
    opens phase to xstream;
    opens authoring to xstream;
    opens gameObjects to xstream;
    opens grids to xstream;
    opens authoringUtils.exception to xstream;
    opens authoringUtils.frontendUtils to xstream;
    opens graph to xstream;
    opens groovy.graph.blocks.core to xstream;

    requires org.codehaus.groovy;
    requires xstream;

    exports authoring;
    exports conversion.engine;
    exports conversion.authoring;
    exports phase.api;
    exports groovy.api;
    exports groovy.graph.blocks.core;
    exports gameObjects;
    exports grids;

    exports authoringUtils.frontendUtils;
    exports authoringUtils.exception;
    exports gameObjects.category;
    exports gameObjects.entity;
    exports gameObjects.sound;
    exports gameObjects.tile;
    exports gameObjects.gameObject;
    exports gameObjects.crud;
    exports gameObjects.player;
    exports utility;
}