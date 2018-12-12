package launchingGame;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import playing.MainPlayer;
import social.Icon;
import social.User;

public class GameIcon extends Icon {

    public GameIcon(String gameName, String description, String reference, String color, String imagePath,
                    String tags, User user) {
        super(gameName, description, reference, color, imagePath, tags, user, "Play", "src/controller/resources/game" +
                "-images/");
    }

    @Override
    public void initButtonHandlers() {
        myButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myButton.getStyleClass().remove(BUTTON_CSS_NORMAL);
                myButton.getStyleClass().add(BUTTON_CSS_HOVER);
            }
        });

        myButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myButton.getStyleClass().remove(BUTTON_CSS_HOVER);
                myButton.getStyleClass().add(BUTTON_CSS_NORMAL);
            }
        });
        myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(String.format("%s, %s", myReferencePath, myName));
                new MainPlayer(myUser, myReferencePath, myName);
            }
        });
    }
}
