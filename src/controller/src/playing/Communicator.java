package playing;

import gameplay.Entity;
import gameplay.GameData;
//import gameplay.Tag;
import gameplay.Tile;
import gameplay.Communicable;
import playingGame.DisplayData;
import playingGame.EntityView;

public class Communicator implements Communicable {
    DisplayData myDisplayData;

    public Communicator(DisplayData displayData){
        myDisplayData = displayData;

    }
/*
    @Override
    public void addNewEntity(Tag tag) {
        EntityView nwEntity = new EntityView();
        if(tag.getType().equals(Tile.class)){
            Tile curTile = GameData.getTile(tag.getID());
            nwEntity.changeImage(curTile.getImagePath());
            nwEntity.changeCoordinates(curTile.getXCoord(), curTile.getYCoord());
        }
        else {
            Entity curEntity = GameData.getEntity(tag.getID());
            nwEntity.changeImage(curEntity.getImagePath());
            nwEntity.changeCoordinates(curEntity.getXCoord(), curEntity.getYCoord());
        }
        myDisplayData.addNewEntity(nwEntity);
    }
    */
}
