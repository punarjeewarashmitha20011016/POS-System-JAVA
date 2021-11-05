package controller;

import bo.BOFactory;
import bo.custom.IncomeBO;
import bo.custom.MakeAnOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CommonFunctions;
import util.Validator;
import view.tdm.CartTm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static controller.LoginFormController.setName;

public class MakeAnOrderFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNic;
    public JFXTextField txtCustomerCity;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemSize;
    public JFXTextField txtItemQty;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtQtyOnHand;
    public TableView<CartTm> tblAddToCart;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscountPrice;
    public TableColumn colTotalAmount;
    public Label lblSetOrderId;
    public Label lblTotalAmount;
    public int selectedRowIndex = -1;
    public LocalDate dateNow;
    public String timeNow;
    public JFXButton placeOrderId;
    public JFXButton addToCartId;
    public JFXButton clearCartId;
    public JFXTextField txtAddDiscount;
    public Label lblDiscountAmount;
    public Label lblSubTotal;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]+$");
    Pattern discountPattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");


    private MakeAnOrderBO orderBO = (MakeAnOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MakeAnOrder);
    private IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Income);

    public void initialize() {
        validateQtyOnHandAndDiscountFields();
        getDateTime();
        setOrderId();
        try {
            getCustomerComboIds();
            getItemCodes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("itemQtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colDiscountPrice.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblAddToCart.refresh();
        tblAddToCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowIndex = (int) newValue;
        });
    }
    public void validateQtyOnHandAndDiscountFields() {
        map.put(txtQtyOnHand, qtyOnHandPattern);
        map.put(txtAddDiscount, discountPattern);
    }
    public void getCustomerComboIds() throws SQLException {
        cmbCustomerId.getItems().addAll(orderBO.getCustomerIds());
        addToCartId.setDisable(true);
        placeOrderId.setDisable(true);
        clearCartId.setDisable(true);
    }

    public void getItemCodes() throws SQLException {
        cmbItemCode.getItems().addAll(orderBO.getItemCodes());
    }

    public void comboCustomerId(ActionEvent actionEvent) {
        try {
            if (!cmbCustomerId.getSelectionModel().isEmpty()) {
                CustomerDTO customerDTO = orderBO.searchCustomer(cmbCustomerId.getValue());
                if (cmbCustomerId.getValue().equals(customerDTO.getCustomerId())) {
                    txtCustomerName.setText(customerDTO.getCustomerName());
                    txtCustomerAddress.setText(customerDTO.getCustomerAddress());
                    txtCustomerNic.setText(customerDTO.getCustomerNic());
                    txtCustomerCity.setText(customerDTO.getCustomerCity());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void comboItemCode(ActionEvent actionEvent) {
        try {
            if (!cmbItemCode.getSelectionModel().isEmpty()) {
                ItemDTO itemDTO = orderBO.searchItem(cmbItemCode.getValue());
                if (cmbItemCode.getValue().equals(itemDTO.getItemCode())) {
                    txtItemDescription.setText(itemDTO.getItemDescription());
                    txtItemSize.setText(String.valueOf(itemDTO.getSize()));
                    txtItemQty.setText(String.valueOf(itemDTO.getQty()));
                    txtItemUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
                }
                addToCartId.setDisable(false);
                clearCartId.setDisable(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddToCart(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getValue();
        String itemDescription = txtItemDescription.getText();
        int itemQty = Integer.parseInt(txtItemQty.getText());
        int itemQtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal itemUnitPrice = new BigDecimal(txtItemUnitPrice.getText());
        BigDecimal nonDeductedPrice = (itemUnitPrice).multiply(BigDecimal.valueOf(itemQtyOnHand));
        BigDecimal discount = BigDecimal.valueOf(0.0);
        if (txtAddDiscount.getText().isEmpty()) {
            discount = BigDecimal.valueOf(0);
        } else {
            double discountInDecimal = Double.parseDouble(txtAddDiscount.getText()) / 100;
            discount = (nonDeductedPrice).multiply(BigDecimal.valueOf(discountInDecimal));
        }
        BigDecimal total = (nonDeductedPrice).subtract(discount);
        if (itemQty < itemQtyOnHand) {
            new Alert(Alert.AlertType.WARNING, "Invalid Quantity", ButtonType.CLOSE).show();
            return;
        }
        boolean b = tblAddToCart.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

        if (b == false) {
            tblAddToCart.getItems().add(new CartTm(itemCode, itemDescription, itemQtyOnHand, itemUnitPrice, discount, total));
        } else {
            CartTm cart = tblAddToCart.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();
            cart.setItemQtyOnHand(cart.getItemQtyOnHand() + itemQtyOnHand);
            cart.setTotal(total.add(cart.getTotal()));
            itemQty = itemQty - itemQtyOnHand;
            tblAddToCart.refresh();
        }
        BigDecimal totalCostOfTheOrder = calculateTotalCost();
        lblSubTotal.setText(String.valueOf(calculateTotalCost().subtract(calculateDiscount())));
        lblTotalAmount.setText(String.valueOf(totalCostOfTheOrder));
        lblDiscountAmount.setText(String.valueOf(calculateDiscount()));
        placeOrderId.setDisable(false);
        txtQtyOnHand.clear();
        txtAddDiscount.clear();
    }


    public BigDecimal calculateTotalCost() {
        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (CartTm temp : tblAddToCart.getItems()
        ) {
            totalCost = totalCost.add(temp.getTotal());
        }
        return totalCost;
    }

    public BigDecimal calculateDiscount() {
        BigDecimal discount = BigDecimal.valueOf(0.0);
        for (CartTm temp : tblAddToCart.getItems()
        ) {
            discount = discount.add(temp.getDiscountPrice());
        }
        return discount;
    }

    public void btnClear(ActionEvent actionEvent) {
        if (selectedRowIndex == -1) {
            new Alert(Alert.AlertType.WARNING, "Select a row from the Table", ButtonType.CLOSE).show();
        } else {
            tblAddToCart.getItems().remove(selectedRowIndex);
            BigDecimal totalCost = calculateTotalCost();
            lblTotalAmount.setText(String.valueOf(totalCost));
            tblAddToCart.refresh();
        }
    }

    public void getDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*dateNow = simpleDateFormat.format(date);*/
        String d = simpleDateFormat.format(date);
        dateNow = LocalDate.parse(d);
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeNow = (currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void setOrderId() {
        try {
            lblSetOrderId.setText(orderBO.generateOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnPlaceOrder(ActionEvent actionEvent) throws SQLException {
        boolean b = saveOrder(lblSetOrderId.getText(), cmbCustomerId.getValue(), tblAddToCart.getItems().stream().map(tm -> new ItemDetailsDTO(tm.getItemCode(), tm.getItemQtyOnHand(), tm.getDiscountPrice(), tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            IncomeDTO incomeDTO = new IncomeDTO(dateNow, Double.parseDouble(lblTotalAmount.getText()));
            if (orderBO.ifIncomeExists()) {
                if (orderBO.ifIncomeDateExists(dateNow)) {
                    orderBO.updateIncomeInTheSameDate(dateNow, Double.parseDouble(lblTotalAmount.getText()));
                    System.out.println("Income Updated");
                } else {
                    boolean b1 = orderBO.saveIncome(incomeDTO);
                    if (b1) {
                        System.out.println("Income Saved");
                    } else {
                        return;
                    }
                }
            } else {
                IncomeDTO i = orderBO.getLastIncomeRecorded();
                IncomeDTO incomeDTONext = new IncomeDTO(dateNow, i.getTotalIncome() + Double.parseDouble(lblTotalAmount.getText()));
                if (orderBO.saveIncome(incomeDTONext)) {
                    System.out.println("Income Saved");
                } else {
                    return;
                }
            }
            CommonFunctions.setNotificationSuccess("Order Placed", "Order Id - " + lblSetOrderId.getText() + " order is successfully placed");
            try {
                generateInvoice();
            } catch (JRException e) {
                e.printStackTrace();
            }
            setOrderId();
            tblAddToCart.getItems().clear();
            lblTotalAmount.setText("");
            lblDiscountAmount.setText("");
            lblSubTotal.setText("");
            cmbCustomerId.getSelectionModel().clearSelection();
            cmbItemCode.getSelectionModel().clearSelection();
            txtCustomerName.clear();
            txtCustomerCity.clear();
            txtCustomerNic.clear();
            txtCustomerAddress.clear();
            txtAddDiscount.clear();
            txtItemDescription.clear();
            txtItemSize.clear();
            txtItemQty.clear();
            txtItemUnitPrice.clear();
            placeOrderId.setDisable(true);
            clearCartId.setDisable(true);
            addToCartId.setDisable(true);
        } else {
            CommonFunctions.setNotificationWarning("Try Again", "Order Id - " + lblSetOrderId.getText() + " order is not placed successfully, Please Try Again..");
        }
    }

    private boolean saveOrder(String orderId, String customerId, List<ItemDetailsDTO> orderDetails) throws SQLException {
        ArrayList<ItemDetailsDTO> itemDetailsDTOS = new ArrayList<>();
        for (ItemDetailsDTO dto : orderDetails
        ) {
            itemDetailsDTOS.add(new ItemDetailsDTO(dto.getItemCode(), dto.getItemQtyOnHand(), dto.getDiscountPrice(), dto.getUnitPrice()));
        }
        BigDecimal totalCost = calculateTotalCost();
        OrderDTO ord = new OrderDTO(orderId, customerId, dateNow, timeNow, totalCost, itemDetailsDTOS);
        return orderBO.purchaseOrder(ord);
    }

    public void generateInvoice() throws JRException {
        ObservableList<CartTm> items = tblAddToCart.getItems();
        HashMap map = new HashMap();
        map.put("orderId", lblSetOrderId.getText());
        map.put("customerName", txtCustomerName.getText());
        map.put("cashierName", setName);
        map.put("subTotal", Double.parseDouble(lblSubTotal.getText()));
        map.put("discount", Double.parseDouble(lblDiscountAmount.getText()));
        map.put("total", Double.parseDouble(lblTotalAmount.getText()));
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/SalesInvoice.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(items.toArray()));
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void qtyEvent(KeyEvent event) {
        if (qtyOnHandPattern.matcher(txtQtyOnHand.getText()).matches()) {
            txtQtyOnHand.setStyle("-fx-border-color: green");
            addToCartId.setDisable(false);
        } else {
            addToCartId.setDisable(true);
            txtQtyOnHand.setStyle("-fx-border-color: red");
        }
    }

    public void discountEvent(KeyEvent event) {
        if (discountPattern.matcher(txtAddDiscount.getText()).matches()) {
            txtAddDiscount.setStyle("-fx-border-color: green");
            addToCartId.setDisable(false);
        } else {
            addToCartId.setDisable(true);
            txtAddDiscount.setStyle("-fx-border-color: red");
        }
    }
}
