package launchingGame;

import javafx.scene.layout.TilePane;
import launching.GameParser;
import social.EngineEvent;
import social.EventBus;
import social.User;

import java.util.ArrayList;
import java.util.List;

public class LauncherGamesDisplay implements Searchable, Sortable {
    public static final String CSS_PATH = "launcher-games-display";
    public static final int COLUMN_NUMBER = 4;
    public static final String CURRENT_FOLDER_KEY = "user.dir";
    public static final String GAMES_PATH = "/src/controller/resources/game-descriptions/";


    public static final double HOR_SPACING = 29;
    public static final double VER_SPACING = 20;

    private TilePane myPane;
    private List<GameIcon> myGames;
    private List<GameIcon> myActiveGames;
    private User myUser;

    public LauncherGamesDisplay() {
        initTiles();
        initGames();
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);
    }

    private void initTiles() {
        myPane = new TilePane();
        myPane.getStyleClass().add(CSS_PATH);
        myPane.setPrefColumns(COLUMN_NUMBER);
        myPane.setHgap(HOR_SPACING);
        myPane.setVgap(VER_SPACING);

    }

    private void initGames() {
        GameParser myParser = new GameParser(System.getProperty(CURRENT_FOLDER_KEY) + GAMES_PATH, myUser);
        myGames = myParser.getMyGames();
        myActiveGames = new ArrayList<>();
        for (GameIcon myIcon : myGames) {
            myPane.getChildren().add(myIcon.getView());
            myActiveGames.add(myIcon);
        }
    }

    @Override
    public void showByTag(String tag) {
        for (GameIcon icon : myActiveGames) {
            myPane.getChildren().remove(icon.getView());
        }
        myActiveGames = new ArrayList<>();
        for (GameIcon icon : myGames) {
            if (icon.checkTag(tag)) {
                myActiveGames.add(icon);
                myPane.getChildren().add(icon.getView());
            }
        }
    }

    @Override
    public void showAll() {
        for (GameIcon icon : myActiveGames) {
            myPane.getChildren().remove(icon.getView());
        }
        myActiveGames = new ArrayList<>();
        for (GameIcon icon : myGames) {
            myActiveGames.add(icon);
            myPane.getChildren().add(icon.getView());
        }
    }

    @Override
    public void showFavorites() {
        if (myUser == null) return;
        for (GameIcon icon : myActiveGames) {
            myPane.getChildren().remove(icon.getView());
        }
        myActiveGames = new ArrayList<>();
        for (String game : myUser.getFavorites()) {
            for (GameIcon icon : myGames) {
                if (icon.checkName(game)) {
                    myActiveGames.add(icon);
                    myPane.getChildren().add(icon.getView());
                }
            }
        }
    }

    public void sortByAlphabet() {
        myActiveGames.sort(new NameComparator());
        for (GameIcon icon : myActiveGames) {
            myPane.getChildren().remove(icon.getView());
        }
        for (GameIcon icon : myActiveGames) {
            myPane.getChildren().add(icon.getView());
        }
    }

    public TilePane getView() {
        return myPane;
    }

    private void reassignUser(Object... args) {
        myUser = (User) args[0];
    }

    private void resetUser(Object... args) {
        myUser = null;
    }
}
