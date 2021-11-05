package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public String generateCustomerId() throws SQLException;
    public ArrayList<String>getCustomerIds() throws SQLException;
    public boolean checkIfNicIsExists(String nic) throws SQLException;
    public boolean ifCustomerExists(String id) throws SQLException;
}
