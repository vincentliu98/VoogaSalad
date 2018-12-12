package social;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class User {
    private static final String IMAGES_FOLDER_PATH = "src/controller/resources/profile-images/";
    private int myID;
    private String myUsername;
    private Set<String> myFavoriteGames;
    private Twitter myTwitter;
    private Set<String> myFollowing;
    private Map<String, String> myProgress;
    private String myImageReference;
    private String myStatus;

    @XStreamOmitField
    private ResourceBundle myErrors;

    public User(int id, String username) {
        myID = id;
        myUsername = username;
        myFavoriteGames = new HashSet<>();
        myFollowing = new HashSet<>();
        myTwitter = null;
        myProgress = new HashMap<>();
        myImageReference = "person_logo.png";
        myStatus = "";
        changeAvatar("person_logo.png");
    }

    public int getID(){ return myID; }

    public ImageView getAvatar(){
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream(IMAGES_FOLDER_PATH + myImageReference)));
            return imageView;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus(String message) {
        myStatus = message;
    }

    public String getStatus() {
        return myStatus;
    }

    public String getImageReference() {
        return myImageReference;
    }

    public void changeAvatar(String imageReference) {
        myImageReference = imageReference;
    }

    public void addFavorite(String gameName) {
        myFavoriteGames.add(gameName);
    }

    public void removeFavorite(String gameName) {
        if (!myFavoriteGames.contains(gameName)) return;
        myFavoriteGames.remove(gameName);
    }

    public void saveGameState(String gameName, String xmlString) {
        myProgress.put(gameName, xmlString);
    }

    public String getGameState(String gameName) {
        if (!myProgress.keySet().contains(gameName)) return "";
        return myProgress.get(gameName);
    }

    public String getUsername() {
        return myUsername;
    }

    public void addFollower(String username) {
        myFollowing.add(username);
        System.out.println("MyFollowing of " + myUsername + " is now " + myFollowing.toString());
    }

    public void removeFollower(String username) {
        if (!myFollowing.contains(username)) return;
        myFollowing.remove(username);
        System.out.println("MyFollowing of " + myUsername + " is now " + myFollowing.toString());
    }

    public Set<String> getFollowing() {
        return myFollowing;
    }

    public Set<String> getFavorites() {
        return myFavoriteGames;
    }

    /**
     * Taken from https://xmeng.wordpress.com/2011/07/10/how-to-handle-sign-in-with-twitter-using-twitter4j/
     */
    public RequestToken getTwitterRequestToken(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Djd5Tus6kdSCXWC471CrJTE7O")
                .setOAuthConsumerSecret("qKdKyeWvbmOWTe1ZDXfLQT34p8GEmWNMoaVbLdde6V5T6MhCTo");
        TwitterFactory tf = new TwitterFactory(cb.build());
        myTwitter = tf.getInstance();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            return myTwitter.getOAuthRequestToken("oob"); // directs to pin page
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void removeTwitter(){
        myTwitter = null;
    }

    public void verifyPin(String pin, RequestToken requestToken){
        try{
            AccessToken accessToken = myTwitter.getOAuthAccessToken(requestToken, pin);
        } catch (Exception e){
            // invalid pin
            e.printStackTrace();
            myTwitter = null;
        }
    }

    public boolean isTwitterConfigured(){
        return myTwitter != null;
    }

    public void tweet(String message) {
        try {
            myTwitter.updateStatus(message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
