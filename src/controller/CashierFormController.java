package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CommonFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static controller.LoginFormController.setName;

public class CashierFormController {
    public BorderPane cashierFormPane;
    public AnchorPane cashierChildPane;
    public Label lblSetName;
    public Label lblTime;
    public ImageView imgWheel2;
    public ImageView imgWheel1;
    public ImageView imgNotification;
    public Circle circle;
    public Label lblNotificationCount;
    public static ArrayList<Label> labelArrayListCashier = new ArrayList<>();
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {
        animateCogWheels();
        String lblName = setName;
        lblSetName.setText("Hi " + lblName);
        getTime();
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
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
                                lblTime.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/" + fileName + ".fxml");
        Parent parent = FXMLLoader.load(resource);
        cashierChildPane.getChildren().clear();
        cashierChildPane.getChildren().add(parent);
    }

    public void btnAddNewCustomer(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("AddCustomerForm");
    }

    public void btnViewItemDetails(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("ViewItemDetailsForm");
    }

    public void btnMakeAnOrder(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("MakeAnOrderForm");
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) cashierFormPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void btnViewCustomerDetails(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("ViewCustomerDetailsForm");
    }

    public void btnViewOrderDetails(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("OrderDetailsForm");
    }

    public void btnReturns(ActionEvent actionEvent) throws IOException {
        animateCogWheels();
        loadUi("ReturnsForm");
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
            labelArrayListCashier.add(label);
        }
    }

    public void showNotifications(MouseEvent mouseEvent) throws IOException {
        loadUi("NotificationsForm");
    }
}
