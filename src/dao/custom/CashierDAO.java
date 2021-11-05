package dao.custom;

import dao.CrudDAO;
import entity.Cashier;

import java.sql.SQLException;

public interface CashierDAO extends CrudDAO<Cashier,String> {
    public String generateCashierId() throws SQLException;
    public boolean cashierLogin(String email,String password) throws SQLException;
    public boolean checkNicAndUserNameIsExists(String name,String nic) throws SQLException;
    public boolean ifCashierIdExists(String id) throws SQLException;
}
