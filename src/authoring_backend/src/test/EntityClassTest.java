package test;

import entities.EntitiesCRUDInterface;
import entities.SimpleEntitiesCRUD;
import entities.TileClass;

public class EntityClassTest {
    public static void main (String args[]) {
        EntitiesCRUDInterface eci = new SimpleEntitiesCRUD();
        eci.createTileClass("demoClass");

        TileClass demo = eci.getTileClass("demoClass");
        demo.setDefaultHeightWidth(1, 1);
        demo.addProperty("color", 14);




    }
}
