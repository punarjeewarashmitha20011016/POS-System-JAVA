package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO Customer VALUES(?,?,?,?,?)",customer.getCustomerId(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerNic(),customer.getCustomerCity());
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.getExecuteUpdate("UPDATE Customer SET customerName=?,customerAddress=?,customerNic=?,city=? where customerId=?",customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerNic(),customer.getCustomerCity(),customer.getCustomerId());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.getExecuteUpdate("DELETE FROM Customer WHERE customerId=?",s);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM Customer");
        ArrayList<Customer>customers=new ArrayList<>();
        while (rst.next()){
            customers.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)));
        }return customers;
    }

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM Customer WHERE customerId=?", id);
        while (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
        }
        return null;
    }

    @Override
    public String generateCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT customerId FROM Customer order by customerId desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "C-00"+temp;
            }else if (temp<99){
                return "C-0"+temp;
            }else {
                return "C-"+temp;
            }
        }else {
            return "C-001";
        }
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT customerId FROM Customer");
        ArrayList<String>customerArrayList=new ArrayList<>();
        while (rst.next()){
            customerArrayList.add(rst.getString(1));
        }
        return customerArrayList;
    }

    @Override
    public boolean checkIfNicIsExists(String nic) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT customerNic from Customer where customerNic=?",nic).next();
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT * FROM Customer WHERE customerId=?", id).next();
    }
}
