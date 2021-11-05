package controller;

import bo.BOFactory;
import bo.custom.CashierBO;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CashierDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.CommonFunctions;
import util.Validator;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageCashierFormController {
    public JFXTextField txtCashierId;
    public JFXTextField txtCashierName;
    public JFXTextField txtCashierNic;
    public JFXTextField txtCashierEmail;
    public JFXTextField txtCashierPassword;
    public JFXButton updateBtnId;
    public JFXButton deleteBtnId;
    public JFXButton saveBtnId;
    public Label lblSetCashierId;

    private CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public Pattern idPattern = Pattern.compile("^(CA-)[0-9]{3}$");
    public Pattern namePattern = Pattern.compile("^[A-z ]{4,}$");
    public Pattern nicPattern = Pattern.compile("^([0-9V]{10}|[0-9]{12})$");
    public Pattern emailPattern = Pattern.compile("^[A-z0-9]{4,}[@](gmail|yahoo)[.](com|lk|uk|[A-z]{1,})$");
    public Pattern passwordPattern = Pattern.compile("^[A-z0-9.@$!#%&?/]{2,}$");

    public void initialize() throws SQLException {
        lblSetCashierId.setText(getCashierId());
        validateFields();
        String id = getCashierId();
        txtCashierId.setText(id);
    }

    private void validateFields() {
        map.put(txtCashierId, idPattern);
        map.put(txtCashierName, namePattern);
        map.put(txtCashierNic, nicPattern);
        map.put(txtCashierEmail, emailPattern);
        map.put(txtCashierPassword, passwordPattern);
    }

    public String getCashierId() throws SQLException {
        return cashierBO.generateCashierId();
    }

    public void btnSaveCashier(ActionEvent actionEvent) {
        CashierDTO cashierDTO = new CashierDTO(txtCashierId.getText(), txtCashierName.getText(), txtCashierNic.getText(), txtCashierEmail.getText(), txtCashierPassword.getText());
        try {
            if (cashierBO.checkNicAndUserNameIsExists(txtCashierName.getText(), txtCashierNic.getText()) || customerBO.checkIfNicIsExists(txtCashierNic.getText())) {
                CommonFunctions.setNotificationWarning("Try Again", "User name or nic is already exists");
                saveBtnId.setDisable(true);
                return;
            } else {
                if (cashierBO.addCashier(cashierDTO)) {
                    CommonFunctions.setNotificationSuccess("Cashier Added", "Adding Cashier is Successful");
                    txtCashierId.setText(getCashierId());
                    clearFields();
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Adding Cashier is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnGenerateCashierId(ActionEvent actionEvent) {
        try {
            txtCashierId.setText(getCashierId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearFields() {
        txtCashierName.clear();
        txtCashierNic.clear();
        txtCashierPassword.clear();
        txtCashierEmail.clear();
    }

    public void validateEvent(KeyEvent event) {
        Object response = Validator.validate(map, saveBtnId);
        if (response instanceof Boolean) {
            saveBtnId.setDisable(false);
            updateBtnId.setDisable(false);
        }
        if (event.getCode().equals(KeyCode.TAB)) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                updateBtnId.setDisable(true);
                txt.requestFocus();
            }
        }

        txtCashierId.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    searchCashierDetails();
                    if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                        clearFields();
                        txtCashierId.requestFocus();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void searchCashierDetails() throws SQLException {
        /*if (!cashierBO.ifCashierIdExists(txtCashierId.getText())) {
            CommonFunctions.setNotificationWarning("Try Again", "Cashier Id doesnt exists");
            return;
        }*/
        if (txtCashierId.getText().length() == 6 && cashierBO.ifCashierIdExists(txtCashierId.getText())) {
            CashierDTO cashierDTO = cashierBO.searchCashier(txtCashierId.getText());
            txtCashierName.setText(cashierDTO.getName());
            txtCashierNic.setText(cashierDTO.getNic());
            txtCashierEmail.setText(cashierDTO.getEmail());
            txtCashierPassword.setText(cashierDTO.getPassword());
        }
    }

    public void btnUpdateCashier(ActionEvent actionEvent) {
        CashierDTO cashierDTO = new CashierDTO(txtCashierId.getText(), txtCashierName.getText(), txtCashierNic.getText(), txtCashierEmail.getText(), txtCashierPassword.getText());
        try {
            if (cashierBO.updateCashier(cashierDTO)) {
                CommonFunctions.setNotificationSuccess("Updated", "Cashier details updated successfully");
                clearFields();
                getCashierId();
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Updating cashier details is unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnDeleteCashier(ActionEvent actionEvent) {
        try {
            if (cashierBO.deleteCashier(txtCashierId.getText())) {
                CommonFunctions.setNotificationSuccess("Cashier deleted", "Deleting cashier details is successful");
                clearFields();
                getCashierId();
            } else {
                CommonFunctions.setNotificationWarning("Try again", "Deleting cashier details is unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
