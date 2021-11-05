package controller;

import bo.BOFactory;
import bo.custom.IncomeBO;
import bo.custom.ItemBO;
import bo.custom.MakeAnOrderBO;
import bo.custom.ReturnsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import dto.ReturnsDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.CommonFunctions;
import view.tdm.ReturnsTm;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnsFormController {
    public TableView<ReturnsTm> tblReturnItemView;
    public TableColumn tblReturnId;
    public TableColumn tblOrderId;
    public TableColumn tblCustomerId;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblReturnQty;
    public TableColumn tblReturnReason;
    public TableColumn tblTotal;
    public JFXTextField txtReturnId;
    public JFXTextField txtReturnOrderId;
    public JFXTextField txtReturnCustomerId;
    public JFXTextField txtReturnItemCode;
    public JFXTextField txtReturnItemDescription;
    public JFXTextField txtReturnQty;
    public JFXTextField txtReturnReason;
    public JFXTextField txtReturnItemPrice;
    public JFXButton acceptReturnBtnId;
    public JFXButton clearBtnId;
    public TextField txtSearchOrderDetails;
    public JFXTextField txtCustomerId;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderTime;
    public JFXTextField txtOrderPrice;

    private ReturnsBO returnsBO = (ReturnsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RETURNS);
    private MakeAnOrderBO orderBO = (MakeAnOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MakeAnOrder);
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Income);
    public static String orderIdInReturnsForm;

    public void initialize() {
        tblReturnId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        tblOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblReturnQty.setCellValueFactory(new PropertyValueFactory<>("returnQty"));
        tblReturnReason.setCellValueFactory(new PropertyValueFactory<>("returnReason"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblReturnItemView.refresh();
        try {
            String returnId = returnsBO.generateReturnId();
            txtReturnId.setText(returnId);
            setDataToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToTable() throws SQLException {
        tblReturnItemView.getItems().clear();
        ArrayList<ReturnsDTO> all = returnsBO.getAll();
        for (ReturnsDTO r : all
        ) {
            tblReturnItemView.getItems().add(new ReturnsTm(r.getReturnId(), r.getOrderId(), r.getCustomerId(), r.getItemCode(), r.getItemDescription(), r.getReturnQty(), r.getReturnReason(), r.getTotal()));
        }
    }

    public void btnAcceptReturn(ActionEvent actionEvent) {
        ReturnsDTO dto = new ReturnsDTO(txtReturnId.getText(), txtReturnOrderId.getText(), txtReturnCustomerId.getText(),
                txtReturnItemCode.getText(), txtReturnItemDescription.getText(), Integer.parseInt(txtReturnQty.getText()),
                txtReturnReason.getText(), Double.parseDouble(txtReturnItemPrice.getText()));
        try {
            if (returnsBO.saveReturns(dto)) {
                boolean b = incomeBO.deductIncome(Double.parseDouble(txtReturnItemPrice.getText()));
                if (b){
                    System.out.println("Income Updated");
                    clearOrderFields();
                    clearReturnFields();
                }else {
                    System.out.println("Try Again");
                    return;
                }
                CommonFunctions.setNotificationSuccess("Return Saved", "Returned Item Added Successfully");
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Returning Item is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnClear(ActionEvent actionEvent) {
        clearReturnFields();
    }
    public void clearReturnFields(){
        txtReturnId.clear();
        txtReturnOrderId.clear();
        txtReturnCustomerId.clear();
        txtReturnItemCode.clear();
        txtReturnItemDescription.clear();
        txtReturnQty.clear();
        txtReturnReason.clear();
        txtReturnItemPrice.clear();
    }

    public void searchOrderDetailsEvent(KeyEvent event) {
        String orderId = txtSearchOrderDetails.getText();
        try {
            if (orderId.length() == 5 && orderBO.orderExists(txtSearchOrderDetails.getText())) {
                OrderDTO orderDTO = orderBO.searchOrder(orderId);
                txtCustomerId.setText(orderDTO.getCustomerId());
                txtOrderDate.setText(String.valueOf(orderDTO.getOrderDate()));
                txtOrderTime.setText(orderDTO.getOrderTime());
                txtOrderPrice.setText(String.valueOf(orderDTO.getCost()));
                txtReturnOrderId.setText(orderDTO.getOrderId());
                txtReturnCustomerId.setText(orderDTO.getCustomerId());
            }
            switch (event.getCode()) {
                case BACK_SPACE:
                    clearOrderFields();
                    break;
                default:
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearOrderFields() {
        txtOrderDate.clear();
        txtOrderTime.clear();
        txtOrderPrice.clear();
        txtCustomerId.clear();
    }

    public void btnViewOrderDetails(ActionEvent actionEvent) throws IOException {
        orderIdInReturnsForm = txtSearchOrderDetails.getText();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/OrderItemDetailsForm.fxml"));
        Parent load = loader.load();
        Scene scene = new Scene(load);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Order Item Details");
        primaryStage.show();
    }

    public void returnKeyEvent(KeyEvent event) {
        txtReturnItemCode.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                try {
                    if (txtReturnItemCode.getLength() == 5 && itemBO.ifItemExists(txtReturnItemCode.getText())) {
                        ItemDTO itemDTO = itemBO.searchItem(txtReturnItemCode.getText());
                        txtReturnItemDescription.setText(itemDTO.getItemDescription());
                    }
                    if (e.getCode().equals(KeyCode.BACK_SPACE)) {
                        txtReturnItemDescription.clear();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        txtReturnQty.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                BigDecimal discountInValue = new BigDecimal(0);
                BigDecimal discountInPercentage = new BigDecimal(0);
                BigDecimal unitPrice = new BigDecimal(0);
                BigDecimal deductedDiscountPrice = new BigDecimal(0);
                BigDecimal totalReturningPrice = new BigDecimal(0);
                try {
                    int qtyReturning = Integer.parseInt(txtReturnQty.getText());
                    ArrayList<OrderDetailsDTO> orderDetailsDTOS = orderBO.searchOrderDetails(txtReturnOrderId.getText());
                    ItemDTO itemDTO = itemBO.searchItem(txtReturnItemCode.getText());
                    for (OrderDetailsDTO dto : orderDetailsDTOS
                    ) {
                        if (dto.getItemCode().equals(txtReturnItemCode.getText())) {
                            discountInValue = discountInValue.add(dto.getDiscount());
                            unitPrice = unitPrice.add(BigDecimal.valueOf(itemDTO.getUnitPrice()));
                        }
                    }
                    discountInPercentage = discountInPercentage.add((discountInValue.divide(unitPrice.multiply(BigDecimal.valueOf(qtyReturning)))).multiply(BigDecimal.valueOf(100)));
                    deductedDiscountPrice = deductedDiscountPrice.add((unitPrice.multiply(BigDecimal.valueOf(qtyReturning))).multiply(discountInPercentage.divide(BigDecimal.valueOf(100))));
                    totalReturningPrice = totalReturningPrice.add(unitPrice.subtract(deductedDiscountPrice));
                    txtReturnItemPrice.setText(String.valueOf(totalReturningPrice));

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
