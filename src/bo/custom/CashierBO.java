package bo.custom;

import bo.SuperBO;
import dto.CashierDTO;

import java.sql.SQLException;

public interface CashierBO extends SuperBO {
    public boolean addCashier(CashierDTO c) throws SQLException;

    public boolean updateCashier(CashierDTO c) throws SQLException;

    public boolean deleteCashier(String s) throws SQLException;

    public String generateCashierId() throws SQLException;

    public boolean cashierLogin(String email,String password) throws SQLException;

    public boolean checkNicAndUserNameIsExists(String name,String nic) throws SQLException;

    public CashierDTO searchCashier(String cashierId) throws SQLException;

    public boolean ifCashierIdExists(String id) throws SQLException;
}
