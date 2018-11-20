package launchingGame;

import api.SubView;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class LauncherGamesDisplay implements SubView<TilePane> {
    public static String CSS_PATH = "launcher-games-display";
    public static int COLUMN_NUMBER = 4;

    public static double ICON_WIDTH = 250;
    public static double ICON_HEIGHT = 180;

    public static double HOR_SPACING = 29;
    public static double VER_SPACING = 20;

    private TilePane myPane;
    private ArrayList<GameIcon> myGames;

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
        myGames = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            GameIcon myicon = new GameIcon(ICON_WIDTH, ICON_HEIGHT);
            myGames.add(myicon);
            myPane.getChildren().add(myicon.getView());
        }

    }

    @Override
    public TilePane getView() {
        return myPane;
    }
}
