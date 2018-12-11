package playingGame;

import javafx.scene.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DisplayData implements PropertyChangeListener {

    List<Viewable> myDisplayedEntities;
    Group myGroup;

    public DisplayData(Group group) {
        myDisplayedEntities = new ArrayList<>();
        myGroup = group;
    }

    // Receives a consumer from a viewable object that removes the object from the pane

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Consumer consumer = (Consumer) evt.getNewValue();
        consumer.accept(this);
    }

    public void addNewEntity(Viewable nwEntity) {
        nwEntity.addListener(this);
        myDisplayedEntities.add(nwEntity);
        myGroup.getChildren().add(nwEntity.getImageView());
    }

    public void removeEntity(Viewable rmEntity) {
        myDisplayedEntities.remove(rmEntity);
        myGroup.getChildren().remove(rmEntity);
    }
}
