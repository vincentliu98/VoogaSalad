package playing;

import gameplay.Entity;
import gameplay.GameData;
import gameplay.Tag;
import gameplay.Tile;
import gameplay.Communicable;
import playingGame.DisplayData;
import playingGame.EntityView;

public class Communicator implements Communicable {
    DisplayData myDisplayData;

    public Communicator(DisplayData displayData){
        myDisplayData = displayData;

    }

    @Override
    public void addNewEntity(Tag tag) {
        EntityView nwEntity = new EntityView();
        if(tag.getType().equals(Tile.class)){
            Tile curTile = GameData.getTile(tag.getID());
//            nwEntity.changeImage();
//            nwEntity.changeCoordinates();
        }
        else {
            Entity curEntity = GameData.getEntity(tag.getID());
//            nwEntity.changeImage();
//            nwEntity.changeCoordinates();
        }
        myDisplayData.addNewEntity(nwEntity);
    }
}
