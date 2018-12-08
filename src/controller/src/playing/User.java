package playing;

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
import java.util.Set;

public class User {
    private int myID;
    private Set<String> myFavoriteGames;
    private ImageView myAvatar;
    private Twitter myTwitter;

    public User(int id){
        myID = id;
        myFavoriteGames = new HashSet<>();
        myTwitter = null;
        myAvatar = new ImageView();
        myAvatar.setImage(new Image(getClass().getResourceAsStream("/graphics/default-avatar.png")));
    }

    public void changeAvatar(Image image){
        myAvatar = new ImageView(image);
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
            RequestToken requestToken = myTwitter.getOAuthRequestToken("oob");
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
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
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
