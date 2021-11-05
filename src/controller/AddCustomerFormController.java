package controller;

import bo.BOFactory;
import bo.custom.CashierBO;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.CommonFunctions;
import util.Validator;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNic;
    public JFXTextField txtCustomerCity;
    public Label lblSetCustomerId;
    public JFXButton addCustomerBtnId;
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    private CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);
    private Pattern idPattern = Pattern.compile("^(C-)[0-9]{3}$");
    private Pattern namePattern = Pattern.compile("^[A-z ]{4,}$");
    private Pattern addressPattern = Pattern.compile("^[A-z ]{2,}[A-z0-9 /,.]{2,}$");
    private Pattern nicPattern = Pattern.compile("^([0-9V]{10}|[0-9]{12})$");
    private Pattern cityPattern = Pattern.compile("^[A-z]{2,}$");
    public LinkedHashMap<TextField, Pattern> linkedHashMap = new LinkedHashMap<>();

    public void initialize() {
        validateTextFields();
    }

    public void selectAllCustomerDetails(ActionEvent actionEvent) {
        try {
            CustomerDTO c1 = customerBO.searchCustomer(txtCustomerId.getText());
            if (c1 != null) {
                txtCustomerName.setText(c1.getCustomerName());
                txtCustomerAddress.setText(c1.getCustomerAddress());
                txtCustomerNic.setText(c1.getCustomerNic());
                txtCustomerCity.setText(c1.getCustomerCity());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void validateTextFields() {
        linkedHashMap.put(txtCustomerId, idPattern);
        linkedHashMap.put(txtCustomerName, namePattern);
        linkedHashMap.put(txtCustomerAddress, addressPattern);
        linkedHashMap.put(txtCustomerNic, nicPattern);
        linkedHashMap.put(txtCustomerCity, cityPattern);
    }

    public void btnUpdateCustomer(ActionEvent actionEvent) {
        CustomerDTO c1 = new CustomerDTO(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtCustomerNic.getText(), txtCustomerCity.getText());
        try {
            if (customerBO.updateCustomer(c1)) {
                CommonFunctions.setNotificationSuccess("Customer Updated", "Updating Customer is Successful");
                clearFields();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Updating Customer is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnRemoveCustomer(ActionEvent actionEvent) {
        try {
            String id = txtCustomerId.getText();
            if (customerBO.deleteCustomer(id)) {
                CommonFunctions.setNotificationSuccess("Customer Deleted", "Deleting Customer is Successful");
                clearFields();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Deleting Customer details is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddCustomer(ActionEvent actionEvent) {
        CustomerDTO c1 = new CustomerDTO(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtCustomerNic.getText(), txtCustomerCity.getText());
        try {
            if (customerBO.checkIfNicIsExists(txtCustomerNic.getText()) || cashierBO.checkNicAndUserNameIsExists(txtCustomerName.getText(),txtCustomerNic.getText())) {
                CommonFunctions.setNotificationWarning("Try Again", "User Name or Nic is already Exists");
                addCustomerBtnId.setDisable(true);
                return;
            } else {
                if (customerBO.addCustomer(c1)) {
                    CommonFunctions.setNotificationSuccess("Customer Added Successfully", "Adding Customer is Successful");
                    clearFields();
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Adding Customer is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnGenerateCustomerId(ActionEvent actionEvent) {
        try {
            String customerId = customerBO.generateCustomerId();
            lblSetCustomerId.setText(customerId);
            txtCustomerId.setText(lblSetCustomerId.getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void validateEvent(KeyEvent event) {
        Object response = Validator.validate(linkedHashMap, addCustomerBtnId);
        if (response instanceof Boolean) {
            addCustomerBtnId.setDisable(false);
        }
        if (event.getCode().equals(KeyCode.TAB)) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
        txtCustomerId.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    if (txtCustomerId.getText().length()==5 && customerBO.ifCustomerExists(txtCustomerId.getText())){
                        CustomerDTO customerDTO = customerBO.searchCustomer(txtCustomerId.getText());
                        txtCustomerName.setText(customerDTO.getCustomerName());
                        txtCustomerNic.setText(customerDTO.getCustomerNic());
                        txtCustomerAddress.setText(customerDTO.getCustomerAddress());
                        txtCustomerCity.setText(customerDTO.getCustomerCity());
                    }else if (event.getCode().equals(KeyCode.BACK_SPACE)){
                        clearFields();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void clearFields() {
        txtCustomerName.clear();
        txtCustomerNic.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
    }
}
