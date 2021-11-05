package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static controller.AdminFormController.labelArrayListAdmin;
import static controller.CashierFormController.labelArrayListCashier;

public class NotificationsFormController {
    public Rectangle notificationsPane;
    public AnchorPane parentPaneOfVbox;
    public VBox vbox;
    public ArrayList<javafx.scene.control.Label> labelArrayList = new ArrayList<>();
    public ArrayList<javafx.scene.control.Label> labelArrayListOfCashier = new ArrayList<>();
    public ArrayList<Label>getLabelArrayListOfAdmin=new ArrayList<>();

    public void initialize() {
        parentPaneOfVbox.setStyle("-fx-background-color: linear-gradient(#818486,#8d8888)");
        setDataToAdminArrayList();
        setDataToCashierLabelArrayList();

        setNotificationsOfAdmin();
        setNotificationsOfCashier();
    }

    public void setDataToCashierLabelArrayList() {
        labelArrayListOfCashier.clear();
        for (javafx.scene.control.Label lbl : labelArrayListCashier
        ) {
            labelArrayListOfCashier.add(lbl);
        }
    }
    public void setDataToAdminArrayList(){
        getLabelArrayListOfAdmin.clear();
        for (Label lbl:labelArrayListAdmin
             ) {
            getLabelArrayListOfAdmin.add(lbl);
        }
    }

    public void setNotificationsOfAdmin() {
        for (int i = 0; i < getLabelArrayListOfAdmin.size(); i++) {
            if (i % 2 == 0) {
                getLabelArrayListOfAdmin.get(i).setStyle("-fx-background-color: linear-gradient(dimgrey,darkgray);-fx-font-size: 19;-fx-text-fill: white;-fx-translate-x:10;-fx-padding: 10;");
                setDataToLabelsInNotificationsForm(getLabelArrayListOfAdmin, i);
            } else {
                getLabelArrayListOfAdmin.get(i).setStyle("-fx-background-color: linear-gradient(slategrey,dimgrey);-fx-font-size: 19;-fx-text-fill: white;-fx-translate-x: 10;-fx-padding: 10;");
                setDataToLabelsInNotificationsForm(getLabelArrayListOfAdmin, i);
            }
        }
    }

    public void setNotificationsOfCashier() {
        for (int i = 0; i < labelArrayListOfCashier.size(); i++) {
            if (i % 2 == 0) {
                labelArrayListOfCashier.get(i).setStyle("-fx-background-color: linear-gradient(dimgrey,darkgray);-fx-font-size: 19;-fx-text-fill: white;-fx-translate-x:10;-fx-padding: 10;");
                setDataToLabelsInNotificationsForm(labelArrayListOfCashier, i);
            } else {
                labelArrayListOfCashier.get(i).setStyle("-fx-background-color: linear-gradient(slategrey,dimgrey);-fx-font-size: 19;-fx-text-fill: white;-fx-translate-x: 10;-fx-padding: 10;");
                setDataToLabelsInNotificationsForm(labelArrayListOfCashier, i);
            }
        }
    }

    public void setDataToLabelsInNotificationsForm(ArrayList<javafx.scene.control.Label> arrayList, int i) {
        arrayList.get(i).setPrefWidth(600);
        arrayList.get(i).setPrefHeight(63);
        JFXButton button = new JFXButton("Clear");
        button.setStyle("-fx-translate-x: 580;-fx-pref-height: 63;-fx-pref-width: 58.0;-fx-background-color:darkslategrey;-fx-text-fill: white;-fx-font-size: 15;");
        AnchorPane paneOne = new AnchorPane(arrayList.get(i), button);
        AnchorPane paneOneDuplicate = new AnchorPane(new javafx.scene.control.Label(" "));
        vbox.getChildren().addAll(paneOne);
        vbox.getChildren().addAll(paneOneDuplicate);
        clearNotification(arrayList.get(i), button, arrayList);
    }

    public void clearNotification(javafx.scene.control.Label label, JFXButton button, ArrayList<Label> list) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getLabelArrayListOfAdmin.equals(list)) {
                    for (int i = 0; i < getLabelArrayListOfAdmin.size(); i++) {
                        if (label.equals(getLabelArrayListOfAdmin.get(i))) {
                            getLabelArrayListOfAdmin.remove(i);
                            vbox.getChildren().clear();
                            setNotificationsOfAdmin();
                            return;
                        }
                    }
                } else if (labelArrayListOfCashier.equals(list)) {
                    for (int i = 0; i < labelArrayListOfCashier.size(); i++) {
                        if (label.equals(labelArrayListOfCashier.get(i))) {
                            labelArrayListOfCashier.remove(i);
                            vbox.getChildren().clear();
                            setNotificationsOfCashier();
                            return;
                        }
                    }
                }
            }
        });
    }

}
