package playingGame;

import gameplay.Entity;
import gameplay.GameData;
import gameplay.Tag;
import gameplay.Tile;

public class Communicator implements Communicable{
    DisplayData myDisplayData;

    public Communicator(DisplayData displayData){
        myDisplayData = displayData;

    }

    @Override
    public void addNewEntity(Tag tag) {
        if(tag.getType().equals(Tile.class)){
            Tile curTile = GameData.getTile(tag.getID());
        }
        else {
            Entity curEntity = GameData.getEntity(tag.getID());
        }
        //EntityView newEntity = new EntityView()

    }
}
