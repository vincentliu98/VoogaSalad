package social;

import javafx.scene.layout.TilePane;
import launchingGame.NameComparator;
import launchingGame.Searchable;
import launchingGame.Sortable;

import java.util.ArrayList;
import java.util.List;

public class LauncherSocialDisplay implements Sortable, Searchable {
    public static final String CSS_PATH = "launcher-games-display";
    public static final int COLUMN_NUMBER = 4;
    public static final double HOR_SPACING = 29;
    public static final double VER_SPACING = 20;
    private static LauncherSocialDisplay single_instance = null;
    private TilePane myPane;
    private List<UserIcon> myUsers;
    private List<UserIcon> myActiveUsers;
    private User myUser;

    public LauncherSocialDisplay(User user) {
        myUser = user;
        initTiles();
        initUsers();
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);
    }

    public static LauncherSocialDisplay getInstance(User user) {
        if (single_instance == null) {
            single_instance = new LauncherSocialDisplay(user);
        }
        return single_instance;
    }

    private void initTiles() {
        myPane = new TilePane();
        myPane.getStyleClass().add(CSS_PATH);
        myPane.setPrefColumns(COLUMN_NUMBER);
        myPane.setHgap(HOR_SPACING);
        myPane.setVgap(VER_SPACING);
    }

    private void initUsers() {
        System.out.println("Creating a new UserParser");
        UserParser myParser = new UserParser(myUser);
        myUsers = myParser.getAllUsers();
        myActiveUsers = new ArrayList<>();
        for (UserIcon myIcon : myUsers) {
            myPane.getChildren().add(myIcon.getView());
            myActiveUsers.add(myIcon);
        }
    }

    @Override
    public void showByTag(String tag) {
        if (myUser == null) return;
        clearIcons();
        myActiveUsers = new ArrayList<>();
        if (tag.toLowerCase().equals("following") || tag.toLowerCase().equals("follow")){
            for (String name : myUser.getFollowing()) {
                for (UserIcon icon : myUsers) {
                    if (icon.getName().equals(name)) {
                        myActiveUsers.add(icon);
                        myPane.getChildren().add(icon.getView());
                    }
                }
            }
        }
        if (tag.toLowerCase().equals("followers") || tag.toLowerCase().equals("followed by") || tag.toLowerCase().equals("follower")){
            for (String name : myUser.getFollowers()) {
                for (UserIcon icon : myUsers) {
                    if (icon.getName().equals(name)) {
                        myActiveUsers.add(icon);
                        myPane.getChildren().add(icon.getView());
                    }
                }
            }
        }
    }

    @Override
    public void showAll() {
        clearIcons();
        myActiveUsers = new ArrayList<>();
        for (UserIcon icon : myUsers) {
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
        clearIcons();
        myActiveUsers = new ArrayList<>();
        for (String name : myUser.getFollowing()) {
            for (UserIcon icon : myUsers) {
                if (icon.getName().equals(name)) {
                    myActiveUsers.add(icon);
                    myPane.getChildren().add(icon.getView());
                }
            }
        }
    }

    @Override
    public void sortByAlphabet() {
        myActiveUsers.sort(new NameComparator());
        clearIcons();
        for (UserIcon icon : myActiveUsers) {
            myPane.getChildren().add(icon.getView());
        }
    }

    private void clearIcons(){
        for (UserIcon icon : myActiveUsers) {
            myPane.getChildren().remove(icon.getView());
        }
    }

    public TilePane getView() {
        return myPane;
    }

    private void reassignUser(Object... args) {
        myUser = (User) args[0];
        clearIcons();
        initUsers();
    }

    private void resetUser(Object... args) {
        myUser = null;
        clearIcons();
        initUsers();
    }
}
