package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tdm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCustomerDetailsFormController {
    public TableView<CustomerTm>tblCustomerDetailsVew;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerNic;
    public TableColumn colCustomerCity;
    private CustomerBO customerBO= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerNic.setCellValueFactory(new PropertyValueFactory<>("customerNic"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        try {
            getCustomerDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getCustomerDetails() throws SQLException {
        tblCustomerDetailsVew.getItems().clear();
        ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
        for (CustomerDTO c:allCustomers
             ) {
            tblCustomerDetailsVew.getItems().add(new CustomerTm(c.getCustomerId(),c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerCity()));
        }
    }
}
