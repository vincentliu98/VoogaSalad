package launching;

import launchingGame.GameIcon;
import org.w3c.dom.Document;
import social.EngineEvent;
import social.EventBus;
import social.Subscriber;
import social.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class GameParser {
    public static final String NAME_TAG = "name";
    public static final String DESCRIPTION_TAG = "info";
    public static final String IMAGE_TAG = "displaygraphic";
    public static final String REFERENCE_TAG = "reference";
    public static final String COLOR_TAG = "color";
    public static final String TAGS_TAG = "tags";

    private String mySourcePath;
    private File[] myFiles;
    private List<GameIcon> myGames;
    private DocumentBuilder myDocumentBuilder;
    private User myUser;

    public GameParser(String path, User user) {
        mySourcePath = path;
        myUser = user;
        myGames = new ArrayList<>();
        findFiles();
        try {
            myDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            for (File file : myFiles) {
                generateGame(file);
            }
        } catch (Exception e) {

        }
        EventBus.getInstance().register(EngineEvent.CHANGE_USER, this::reassignUser);
        EventBus.getInstance().register(EngineEvent.LOGGED_OUT, this::resetUser);
    }

    private void findFiles() {
        File folder = new File(mySourcePath);
        myFiles = folder.listFiles();
    }

    private void generateGame(File file) {
        try {
            Document myDocTree = myDocumentBuilder.parse(file);
            myDocTree.getDocumentElement().normalize();
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String name = myDocTree.getElementsByTagName(NAME_TAG).item(0).getTextContent().trim();
            String description = myDocTree.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent().trim();
            String color = myDocTree.getElementsByTagName(COLOR_TAG).item(0).getTextContent().trim();
            String image = myDocTree.getElementsByTagName(IMAGE_TAG).item(0).getTextContent().trim();
            String reference = myDocTree.getElementsByTagName(REFERENCE_TAG).item(0).getTextContent().trim();
            String tags = myDocTree.getElementsByTagName(TAGS_TAG).item(0).getTextContent().trim();
            GameIcon nwIcon = new GameIcon(name, description, reference, color, image, tags, myUser);
            myGames.add(nwIcon);
        } catch (Exception e) {

        }
    }

    public List<GameIcon> getMyGames() {
        return myGames;
    }

    private void reassignUser(Object... args) {
        myUser = (User) args[0];
    }

    private void resetUser(Object... args) {
        myUser = null;
    }
}
