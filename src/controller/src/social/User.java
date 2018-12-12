package social;

import exceptions.ErrorMessage;
import exceptions.UserException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.util.*;

public class User {
    private static final String IMAGES_FOLDER_PATH = "src/controller/resources/profile-images/";
    private int myID;
    private String myUsername;
    private Set<String> myFavoriteGames;
    private Twitter myTwitter;
    private Set<String> myFollowing;
    private Set<String> myFollowers;
    private Map<String, String> myProgress;
    private String myImageReference;
    private String myStatus;

    public User(int id, String username) {
        myID = id;
        myUsername = username;
        myFavoriteGames = new HashSet<>();
        myFollowing = new HashSet<>();
        myFollowers = new HashSet<>();
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
            return new ImageView();
        }
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

    public void follow(String username) {
        myFollowing.add(username);
    }

    public void unfollow(String username) {
        if (!myFollowing.contains(username)) return;
        myFollowing.remove(username);
    }

    public void addFollower(String username) {
        myFollowers.add(username);
    }

    public void removeFollower(String username) {
        if (!myFollowers.contains(username)) return;
        myFollowers.remove(username);
    }

    public Set<String> getFollowing() {
        return myFollowing;
    }

    public Set<String> getFollowers() { return myFollowers; }

    public Set<String> getFavorites() {
        return myFavoriteGames;
    }

    /**
     * Taken from https://xmeng.wordpress.com/2011/07/10/how-to-handle-sign-in-with-twitter-using-twitter4j/
     */
    public RequestToken getTwitterRequestToken(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Djd5Tus6kdSCXWC471CrJTE7O") // Specific to my (Natalie) developer account
                .setOAuthConsumerSecret("qKdKyeWvbmOWTe1ZDXfLQT34p8GEmWNMoaVbLdde6V5T6MhCTo");
        TwitterFactory tf = new TwitterFactory(cb.build());
        myTwitter = tf.getInstance();
        try{
            return myTwitter.getOAuthRequestToken("oob"); // directs to pin page
        } catch (Exception e){
            new ErrorMessage(new UserException("Can not establish connection with Twitter.", "Check authorization consumer keys"));
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
            myTwitter = null;
            new ErrorMessage(new UserException("Invalid pin.", "No Twitter access granted."));
        }
    }

    public boolean isTwitterConfigured(){
        return myTwitter != null;
    }

    public void tweet(String message) {
        try {
            if (myTwitter == null) return;
            myTwitter.updateStatus(message);
        } catch (TwitterException e) {
            new ErrorMessage(new UserException("Can not establish connection with Twitter.", "Check authorization consumer keys"));
        }
    }

}
