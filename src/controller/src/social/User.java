package social;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class User {
    private int myID;
    private Set<String> myFavoriteGames;
    private ImageView myAvatar;
    private Twitter myTwitter;
    private ResourceBundle myErrors;
    private Set<String> myFriends;

    public User(int id){
        myID = id;
        myFavoriteGames = new HashSet<>();
        myFriends = new HashSet<>();
        myTwitter = null;
        myAvatar = new ImageView();
        myAvatar.setImage(new Image(getClass().getResourceAsStream("/graphics/person_logo.png")));
        //myErrors = ResourceBundle.getBundle("resources/errors/Errors");
    }

    public ImageView getAvatar(){
        return myAvatar;
    }

    public void changeAvatar(Image image){
        myAvatar = new ImageView(image);
    }

    public void addFavorite(String gameName){
        myFavoriteGames.add(gameName);
    }

    public void removeFavorite(String gameName){
        if (!myFavoriteGames.contains(gameName)) return;
        myFavoriteGames.remove(gameName);
    }

    public void addFriend(String username){
        myFriends.add(username);
    }

    public void removeFriend(String username){
        myFriends.remove(username);
    }

    public Set<String> getFavorites(){
        return myFavoriteGames;
    }

    /**
     * Taken from https://xmeng.wordpress.com/2011/07/10/how-to-handle-sign-in-with-twitter-using-twitter4j/
     */
    public void configureTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Djd5Tus6kdSCXWC471CrJTE7O")
                .setOAuthConsumerSecret("qKdKyeWvbmOWTe1ZDXfLQT34p8GEmWNMoaVbLdde6V5T6MhCTo");
        TwitterFactory tf = new TwitterFactory(cb.build());
        myTwitter = tf.getInstance();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            RequestToken requestToken = myTwitter.getOAuthRequestToken("oob"); // directs to pin page
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = myTwitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = myTwitter.getOAuthAccessToken(requestToken);
                    }
                } catch (Exception e) {
                    //throw new UserException(myErrors.getString("InvalidTwitterPin"));
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tweet(String message){
        try{
            myTwitter.updateStatus(message);
        } catch (TwitterException e){
            e.printStackTrace();
        }
    }

    public static void main (String[] args){ // TODO: Delete (for testing only)
        User testUser = new User(1);
        testUser.configureTwitter();
    }

}
