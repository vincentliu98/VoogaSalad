package launchingGame;

import javafx.scene.layout.TilePane;
import launching.GameParser;

import java.util.List;

public class LauncherGamesDisplay {
    public static String CSS_PATH = "launcher-games-display";
    public static int COLUMN_NUMBER = 4;


    public static double HOR_SPACING = 29;
    public static double VER_SPACING = 20;

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
        GameParser myParser = new GameParser("/Users/jonathannakagawa/Desktop/Stuff/CompSci308/voogasalad_printstacktrace/src/controller/resources/games");
        myGames = myParser.getMyGames();
        for(GameIcon myIcon: myGames){
            myPane.getChildren().add(myIcon.getView());
        }
//        for(int i = 0; i < 6; i++){
////            GameIcon myicon = new GameIcon("Chess", "blah");
////            myGames.add(myicon);
////            myPane.getChildren().add(myicon.getView());
//        }


    }

    public TilePane getView() {
        return myPane;
    }
}
