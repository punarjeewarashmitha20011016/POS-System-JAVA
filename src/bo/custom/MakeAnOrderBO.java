package bo.custom;

import bo.SuperBO;
import dto.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface MakeAnOrderBO extends SuperBO {
    public boolean purchaseOrder(OrderDTO dto) throws SQLException;
    public String generateOrderId() throws SQLException;
    public ArrayList<String>getCustomerIds() throws SQLException;
    public ArrayList<String>getItemCodes() throws SQLException;
    public CustomerDTO searchCustomer(String id) throws SQLException;
    public ItemDTO searchItem(String code) throws SQLException;
    public ArrayList<OrderDetailsDTO>getAllOrderDetails() throws SQLException;
    public ArrayList<OrderDTO>getAllOrders() throws SQLException;
    public ArrayList<OrderDetailsDTO>searchOrderDetails(String orderId) throws SQLException;
    public OrderDTO searchOrder(String orderId) throws SQLException;
    public boolean orderExists(String orderId) throws SQLException;
    public boolean ifIncomeExists() throws SQLException;
    public boolean ifIncomeDateExists(LocalDate date) throws SQLException;
    public boolean updateIncomeInTheSameDate(LocalDate date, double income) throws SQLException;
    public boolean saveIncome(IncomeDTO dto) throws SQLException;
    public IncomeDTO getLastIncomeRecorded() throws SQLException;
}
