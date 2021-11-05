package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.CommonFunctions;
import util.Validator;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageItemsController {
    public AnchorPane manageItemsPane;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemSize;
    public JFXTextField txtItemQuantity;
    public JFXTextField txtItemBuyingPrice;
    public JFXButton addButtonId;
    public JFXTextField txtItemUnitPrice;
    public Label lblSetItemCode;
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public Pattern codePattern = Pattern.compile("^(I-)[0-9]{3}$");
    public Pattern descriptionPattern = Pattern.compile("^[A-z0-9 ]{2,}$");
    public Pattern sizePattern = Pattern.compile("^[0-9]+[.][0-9]+$");
    public Pattern qtyPattern = Pattern.compile("^[0-9]+$");
    public Pattern buyingPricePattern = Pattern.compile("^[0-9]+[.][0-9]+$");
    public Pattern unitPricePattern = Pattern.compile("^[0-9]+[.][0-9]+$");

    public void initialize() {
        validateTextFields();
    }

    public void clearFields() {
        txtItemDescription.clear();
        txtItemSize.clear();
        txtItemQuantity.clear();
        txtItemBuyingPrice.clear();
        txtItemUnitPrice.clear();
    }

    private void validateTextFields() {
        map.put(txtItemCode, codePattern);
        map.put(txtItemDescription, descriptionPattern);
        map.put(txtItemSize, sizePattern);
        map.put(txtItemQuantity, qtyPattern);
        map.put(txtItemBuyingPrice, buyingPricePattern);
        map.put(txtItemUnitPrice, unitPricePattern);
    }

    public void btnAddItem(ActionEvent actionEvent) {
        ItemDTO i = new ItemDTO(txtItemCode.getText(), txtItemDescription.getText(), Double.parseDouble(txtItemSize.getText()), Integer.parseInt(txtItemQuantity.getText()), Double.parseDouble(txtItemBuyingPrice.getText()), Double.parseDouble(txtItemUnitPrice.getText()));
        try {
            if (itemBO.addItem(i)) {
                CommonFunctions.setNotificationSuccess("Item Added", "Item Added Successfully");
                clearFields();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Adding Item is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnUpdateItem(ActionEvent actionEvent) {
        ItemDTO i = new ItemDTO(txtItemCode.getText(), txtItemDescription.getText(), Double.parseDouble(txtItemSize.getText()), Integer.parseInt(txtItemQuantity.getText()), Double.parseDouble(txtItemBuyingPrice.getText()), Double.parseDouble(txtItemUnitPrice.getText()));
        try {
            if (itemBO.updateItem(i)) {
                CommonFunctions.setNotificationSuccess("Item Updated", "Item Updated Successfully");
                clearFields();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Item Updated is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnRemoveItem(ActionEvent actionEvent) {
        try {
            if (itemBO.deleteItem(txtItemCode.getText())) {
                CommonFunctions.setNotificationSuccess("Item Deleted", "Deleting Item is Unsuccessful");
                clearFields();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Deleting item is unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void validateEvent(KeyEvent event) {
        Object response = Validator.validate(map, addButtonId);
        if (response instanceof Boolean) {
            addButtonId.setDisable(false);
        }
        if (event.getCode().equals(KeyCode.TAB)) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }

        txtItemCode.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    if (txtItemCode.getLength() == 5 && itemBO.ifItemExists(txtItemCode.getText())) {
                        ItemDTO itemDTO = itemBO.searchItem(txtItemCode.getText());
                        txtItemDescription.setText(itemDTO.getItemDescription());
                        txtItemSize.setText(String.valueOf(itemDTO.getSize()));
                        txtItemQuantity.setText(String.valueOf(itemDTO.getQty()));
                        txtItemBuyingPrice.setText(String.valueOf(itemDTO.getBuyingPrice()));
                        txtItemUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
                    } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                        clearFields();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    public void btnGenerateItemCode(ActionEvent actionEvent) {
        try {
            String itemCode = itemBO.generateItemCode();
            txtItemCode.setText(itemCode);
            lblSetItemCode.setText(itemCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
