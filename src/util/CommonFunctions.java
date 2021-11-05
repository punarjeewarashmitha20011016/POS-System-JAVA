package util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class CommonFunctions {
    public static void setNotificationSuccess(String buttonTitle, String functionMessage) {
        String title = buttonTitle;
        String message = functionMessage;
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.millis(8000));
    }

    public static void setNotificationWarning(String buttonTitle, String functionMessage) {
        String title = buttonTitle;
        String message = functionMessage;
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.WARNING);
        notification.showAndDismiss(Duration.millis(8000));
    }
    public static void animatedWheels(ImageView image1,ImageView image2){
        Timeline rotate = new Timeline();
        Timeline rotate1 = new Timeline();
        DoubleProperty r = image1.rotateProperty();
        DoubleProperty r1 = image2.rotateProperty();

        rotate1.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r, -360))
        );
        rotate.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r1, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r1, 360))
        );
        rotate1.play();
        rotate.play();
    }
}
