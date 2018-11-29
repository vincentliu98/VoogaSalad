package launchingGame;

import api.SubView;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class TextOptions implements SubView<HBox>, PropertyChangeListener {
    public static final String MY_GAME_TEXT = "My Games";
    public static final String STORE_TEXT = "Store";
    public static final String SOCIAL_TEXT = "Social";

    private HBox myHbox;

    private ArrayList<OptionHolder> myOptions;
    private OptionHolder myGames;
    private OptionHolder myStore;
    private OptionHolder mySocial;

    public TextOptions(){
        initBox();
        initText();
    }

    public void toggleSelected(OptionHolder selcted){
        for(OptionHolder option: myOptions){
            if(option.equals(selcted)){
                option.select();
            }
            else{
                option.deselect();
            }
        }
    }

    private void initBox(){
        myHbox = new HBox();
        myHbox.setSpacing(20);
        myHbox.setAlignment(Pos.CENTER_LEFT);
    }

    private void initText(){
        myOptions = new ArrayList<>();

        myGames = new OptionHolder(MY_GAME_TEXT);
        myGames.addListener(this);
        myStore = new OptionHolder(STORE_TEXT);
        myStore.addListener(this);
        mySocial = new OptionHolder(SOCIAL_TEXT);
        mySocial.addListener(this);

        myOptions.add(myGames);
        myOptions.add(myStore);
        myOptions.add(mySocial);

        myHbox.getChildren().addAll(myOptions);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("hello");
        toggleSelected((OptionHolder) evt.getNewValue());
    }

    @Override
    public HBox getView() {
        return myHbox;
    }
}
