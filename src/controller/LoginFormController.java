package controller;

import bo.BOFactory;
import bo.custom.CashierBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public AnchorPane loginPane;
    public static String setName;
    public JFXPasswordField txtPassword;
    private CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);

    public Scene loadParent(String path) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource(path));
        Scene scene = new Scene(parent);
        return scene;
    }

    public void loadUi(String path, AnchorPane loginPane) throws IOException {
        Scene scene = loadParent(path);
        Stage primaryStage = (Stage) loginPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void btnLogin(ActionEvent actionEvent) throws IOException {
        try {
            setName = txtUserName.getText();
            if (txtUserName.getText().equals("admin") && txtPassword.getText().equals("1234")) {
                loadUi("../view/AdminForm.fxml", loginPane);
            } else if (cashierBO.cashierLogin(txtUserName.getText(), txtPassword.getText())) {
                loadUi("../view/CashierForm.fxml", loginPane);
            } else {
                new Alert(Alert.AlertType.WARNING, "User Name or Password is not valid", ButtonType.CLOSE).show();
                txtUserName.clear();
                txtPassword.clear();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
