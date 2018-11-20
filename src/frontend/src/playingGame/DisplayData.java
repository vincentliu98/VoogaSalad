package playing;

import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DisplayData implements PropertyChangeListener {

    List<Viewable> myDisplayedEntities;
    Pane myPane;

    public DisplayData(Pane pane){
        myDisplayedEntities = new ArrayList<>();
        myPane = pane;
    }

    // Receives a consumer from a viewable object that removes the object from the pane

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Consumer consumer = (Consumer) evt.getNewValue();
        consumer.accept(this);
    }

    public void addNewEntity(Viewable nwEntity){
        nwEntity.addListener(this);
        myDisplayedEntities.add(nwEntity);
        myPane.getChildren().add(nwEntity.getImageView());
    }

    public void removeEntity(Viewable rmEntity){
        myDisplayedEntities.remove(rmEntity);
        myPane.getChildren().remove(rmEntity);
    }
}
