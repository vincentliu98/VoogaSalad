package social;

import javafx.scene.layout.TilePane;
import launchingGame.NameComparator;
import launchingGame.Searchable;
import launchingGame.Sortable;

import java.util.ArrayList;
import java.util.List;

public class LauncherSocialDisplay implements Sortable, Searchable, Subscriber {
    public static final String CSS_PATH = "launcher-games-display";
    public static final int COLUMN_NUMBER = 4;
    public static final double HOR_SPACING = 29;
    public static final double VER_SPACING = 20;

    private TilePane myPane;
    private List<UserIcon> myUsers;
    private List<UserIcon> myActiveUsers;
    private User myUser;

    public LauncherSocialDisplay(){
        initTiles();
        initUsers();
        myUser = null;
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this);
    }

    private void initTiles(){
        myPane = new TilePane();
        myPane.getStyleClass().add(CSS_PATH);
        myPane.setPrefColumns(COLUMN_NUMBER);
        myPane.setHgap(HOR_SPACING);
        myPane.setVgap(VER_SPACING);
    }

    private void initUsers(){
        UserParser myParser = new UserParser();
        myUsers = myParser.getMyUsers();
        myActiveUsers = new ArrayList<>();
        for(UserIcon myIcon: myUsers){
            myPane.getChildren().add(myIcon.getView());
            myActiveUsers.add(myIcon);
        }
    }

    @Override
    public void showByTag(String tag){ }

    @Override
    public void showAll(){
        for(UserIcon icon: myActiveUsers){
            myPane.getChildren().remove(icon.getView());
        }
        myActiveUsers = new ArrayList<>();
        for(UserIcon icon: myUsers){
            myActiveUsers.add(icon);
            myPane.getChildren().add(icon.getView());
        }
    }

    /**
     * Equivalent of showing everyone the User follows
     */
    @Override
    public void showFavorites() {
        if (myUser == null) return;
        for(UserIcon icon: myActiveUsers){
            myPane.getChildren().remove(icon.getView());
        }
        myActiveUsers = new ArrayList<>();
        for (String name : myUser.getFollowing()){
            for (UserIcon icon : myUsers){
                if (icon.getName().equals(name)){
                    myActiveUsers.add(icon);
                    myPane.getChildren().add(icon.getView());
                }
            }
        }
    }

    @Override
    public void sortByAlphabet(){
        myActiveUsers.sort(new NameComparator());
        for(UserIcon icon: myActiveUsers){
            myPane.getChildren().remove(icon.getView());
        }
        for(UserIcon icon: myActiveUsers){
            myPane.getChildren().add(icon.getView());
        }
    }

    public TilePane getView() {
        return myPane;
    }

    @Override
    public void update(EngineEvent engineEvent, Object ... args) {
        if (engineEvent.equals(EngineEvent.CHANGE_USER) && args[0].getClass().equals(User.class)){
            myUser = (User) args[0];
            System.out.println("Username is " + myUser.getUsername() + "classname is " + getClass().getName());
        }
    }
}
