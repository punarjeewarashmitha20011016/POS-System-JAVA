package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public String generateCustomerId() throws SQLException;

    public ArrayList<String> getCustomerIds() throws SQLException;

    public boolean addCustomer(CustomerDTO c) throws SQLException;

    public boolean updateCustomer(CustomerDTO c) throws SQLException;

    public boolean deleteCustomer(String s) throws SQLException;

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException;

    public CustomerDTO searchCustomer(String id) throws SQLException;

    public boolean checkIfNicIsExists(String nic) throws SQLException;

    public boolean ifCustomerExists(String id) throws SQLException;
}
