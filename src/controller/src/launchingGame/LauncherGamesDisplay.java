package launchingGame;

import javafx.scene.layout.TilePane;
import launching.GameParser;

import java.util.List;

public class LauncherGamesDisplay {
    public static final String CSS_PATH = "launcher-games-display";
    public static final int COLUMN_NUMBER = 4;
    public static final String CURRENT_FOLDER_KEY = "user.dir";
    public static final String GAMES_PATH = "/src/controller/resources/games/";


    public static final double HOR_SPACING = 29;
    public static final double VER_SPACING = 20;

    private TilePane myPane;
    private List<GameIcon> myGames;

    public LauncherGamesDisplay(){
        initTiles();
        initGames();
    }

    private void initTiles(){
        myPane = new TilePane();
        myPane.getStyleClass().add(CSS_PATH);
        myPane.setPrefColumns(COLUMN_NUMBER);
        myPane.setHgap(HOR_SPACING);
        myPane.setVgap(VER_SPACING);

    }

    private void initGames(){
        //GameParser myParser = new GameParser("/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/controller/resources/games");
        GameParser myParser = new GameParser(System.getProperty(CURRENT_FOLDER_KEY) + GAMES_PATH);
        myGames = myParser.getMyGames();
        for(GameIcon myIcon: myGames){
            myPane.getChildren().add(myIcon.getView());
        }

    }

    public TilePane getView() {
        return myPane;
    }
}
