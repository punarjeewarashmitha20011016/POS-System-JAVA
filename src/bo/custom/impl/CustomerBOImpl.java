package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import entity.Customer;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateCustomerId() throws SQLException {
        return customerDAO.generateCustomerId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerDAO.getCustomerIds();
        return customerIds;
    }

    @Override
    public boolean addCustomer(CustomerDTO c) throws SQLException {
        return customerDAO.add(new Customer(c.getCustomerId(), c.getCustomerName(), c.getCustomerAddress(), c.getCustomerNic(), c.getCustomerCity()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO c) throws SQLException {
        return customerDAO.update(new Customer(c.getCustomerId(),c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerCity()));
    }

    @Override
    public boolean deleteCustomer(String s) throws SQLException {
        return customerDAO.delete(s);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<Customer>getAll=customerDAO.getAll();
        ArrayList<CustomerDTO>getCustomers=new ArrayList<>();
        for (Customer c:getAll
             ) {
            getCustomers.add(new CustomerDTO(c.getCustomerId(),c.getCustomerName(),c.getCustomerAddress(),c.getCustomerNic(),c.getCustomerCity()));
        }return getCustomers;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        Customer customer =  customerDAO.search(id);
        return new CustomerDTO(customer.getCustomerId(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerNic(),customer.getCustomerCity());
    }

    @Override
    public boolean checkIfNicIsExists(String nic) throws SQLException {
        return customerDAO.checkIfNicIsExists(nic);
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException {
        return customerDAO.ifCustomerExists(id);
    }
}
