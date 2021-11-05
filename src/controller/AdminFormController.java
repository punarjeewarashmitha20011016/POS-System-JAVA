package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CommonFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class AdminFormController {
    public Label lblSetName;
    public Label lblTime;
    public BorderPane adminFormPane;
    public AnchorPane adminChildPane;
    public ImageView imgWheel1;
    public ImageView imgWheel2;
    public ImageView imgNotification;
    public Label lblNotificationCount;
    public static ArrayList<Label> labelArrayListAdmin = new ArrayList<>();
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {
        animateCogWheels();
        getTime();
        lblSetName.setText("Hi" + " " + "admin");
        try {
            getNotifications();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void animateCogWheels() {
        CommonFunctions.animatedWheels(imgWheel1, imgWheel2);
    }

    public void getTime() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/" + fileName + ".fxml");
        Parent parent = FXMLLoader.load(resource);
        adminChildPane.getChildren().clear();
        adminChildPane.getChildren().add(parent);
    }

    public void btnSystemReports(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("SystemReportsForm");
    }

    public void btnManageItems(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("ManageItemsForm");
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) adminFormPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void getNotifications() throws SQLException {
        ArrayList<ItemDTO> itemDTOS = itemBO.itemsLessThanFive();
        int count = 0;
        for (int i = 0; i < itemDTOS.size(); i++) {
            Label label = new Label();
            label.setStyle("-fx-text-fill: white;");
            label.setStyle("-fx-font-size: 19");
            count++;
            System.out.println(count);
            lblNotificationCount.setText(String.valueOf(count));
            label.setText("Check Item Qty of - " + itemDTOS.get(i).getItemDescription() + " in the stock");
            labelArrayListAdmin.add(label);
        }
    }


    public void showNotifications(MouseEvent mouseEvent) throws IOException {
        animateCogWheels();
        loadUi("NotificationsForm");
    }

    public void btnManageCashier(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("ManageCashierForm");
    }
}
