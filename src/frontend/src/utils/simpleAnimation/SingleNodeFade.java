package utils.simpleAnimation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * This class provides methods that handle the fading in and out of a single Node.
 *
 * @author Haotian Wang
 */
public class SingleNodeFade {
    /**
     * This method returns a FadeTransition that represents fading in animation for a specific Node, that lasts a predefined amount of time.
     *
     * @param node:               A JavaFx Node where the fading occurs.
     * @param timeInMilliSeconds: A double, time in milliseconds for the Node to fully fade in.
     * @return A FadeTransition object representing the animation.
     */
    public static FadeTransition getNodeFadeIn(Node node, double timeInMilliSeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(timeInMilliSeconds));
        fadeIn.setNode(node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        return fadeIn;
    }

    /**
     * This method returns a FadeOutTransition that represents fading out animation for a specific Node, that lasts a predefined amount of time.
     *
     * @param node:               A JavaFx Node where the fading occurs.
     * @param timeInMilliSeconds: A double, time in milliseconds for the Node to fully disappear.
     * @return A FadeTransition object representing the fading out animation.
     */
    public static FadeTransition getNodeFadeOut(Node node, double timeInMilliSeconds) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(timeInMilliSeconds));
        fadeOut.setNode(node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        return fadeOut;
    }

    /**
     * This method returns a FadeTransition that represents fading in and out animation for a specific Node, that lasts a predefined amount of time.
     *
     * @param node:               A JavaFx Node where the fading occurs.
     * @param timeInMilliSeconds: A double, time in milliseconds for the Node to fully enter and disappear.
     * @return A FadeTransition object representing the fading in and out animation.
     */
    public static FadeTransition getNodeFadeInAndOut(Node node, double timeInMilliSeconds) {
        FadeTransition fadeInAndOUt = new FadeTransition(Duration.millis(timeInMilliSeconds));
        fadeInAndOUt.setNode(node);
        fadeInAndOUt.setFromValue(0.0);
        fadeInAndOUt.setToValue(1.0);
        fadeInAndOUt.setCycleCount(2);
        fadeInAndOUt.setAutoReverse(true);
        return fadeInAndOUt;
    }
}
