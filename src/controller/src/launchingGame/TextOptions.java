package launchingGame;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import social.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class TextOptions implements PropertyChangeListener {
    public static final double SIDE_WIDTH = 50;
    public static final String MY_GAME_TEXT = "My Games";
    public static final String STORE_TEXT = "Store";
    public static final String SOCIAL_TEXT = "Social";

    private HBox myHbox;

    private ArrayList<OptionHolder> myOptions;
    private OptionHolder myGames;
    private OptionHolder myStore;
    private OptionHolder mySocial;
    private BorderPane myPane;
    private User myUser;

    public TextOptions(BorderPane pane) {
        initBox();
        initText();
        myPane = pane;
        myUser = null;
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);

    }

    public void toggleSelected(OptionHolder selcted) {
        for (OptionHolder option : myOptions) {
            if (option.equals(selcted)) {
                option.select();
            } else {
                option.deselect();
            }
        }
    }

    private void initBox() {
        myHbox = new HBox();
        myHbox.setSpacing(20);
        myHbox.setAlignment(Pos.CENTER_LEFT);
    }

    private void initText() {
        myOptions = new ArrayList<>();

        myGames = new OptionHolder(MY_GAME_TEXT);
        myGames.addListener(this);
        myGames.setOnClickListener(e -> {
            LauncherGamesDisplay myGameDisplay = new LauncherGamesDisplay();
            myPane.setCenter(myGameDisplay.getView());
            myPane.setLeft(new LauncherSideBarView(SIDE_WIDTH, myGameDisplay).getView());
            EventBus.getInstance().sendMessage(EngineEvent.SWITCH_SEARCHABLE, myGameDisplay);
        });
        myStore = new OptionHolder(STORE_TEXT);
        myStore.addListener(this);
        mySocial = new OptionHolder(SOCIAL_TEXT);
        mySocial.addListener(this);
        mySocial.setOnClickListener(e -> {
            LauncherSocialDisplay mySocialDisplay = LauncherSocialDisplay.getInstance(myUser);
            myPane.setCenter(mySocialDisplay.getView());
            myPane.setLeft(new LauncherSideBarView(SIDE_WIDTH, mySocialDisplay).getView());
            EventBus.getInstance().sendMessage(EngineEvent.SWITCH_SEARCHABLE, mySocialDisplay);
        });

        myOptions.add(myGames);
        myOptions.add(myStore);
        myOptions.add(mySocial);

        myHbox.getChildren().addAll(myOptions);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        toggleSelected((OptionHolder) evt.getNewValue());
    }

    public HBox getView() {
        return myHbox;
    }

    private void reassignUser(Object... args) {
        myUser = (User) args[0];
    }

    private void resetUser(Object... args){
        myUser = null;
    }
}
