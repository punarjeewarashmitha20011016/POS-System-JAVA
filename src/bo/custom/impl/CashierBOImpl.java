package bo.custom.impl;

import bo.custom.CashierBO;
import dao.DAOFactory;
import dao.custom.CashierDAO;
import entity.Cashier;
import dto.CashierDTO;

import java.sql.SQLException;

public class CashierBOImpl implements CashierBO {
    private CashierDAO cashierDAO = (CashierDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CASHIER);

    @Override
    public boolean addCashier(CashierDTO c) throws SQLException {
        return cashierDAO.add(new Cashier(c.getId(), c.getName(), c.getNic(), c.getEmail(), c.getPassword()));
    }

    @Override
    public boolean updateCashier(CashierDTO c) throws SQLException {
        return cashierDAO.update(new Cashier(c.getId(),c.getName(), c.getNic(), c.getEmail(), c.getPassword()));
    }

    @Override
    public boolean deleteCashier(String s) throws SQLException {
        return cashierDAO.delete(s);
    }

    @Override
    public String generateCashierId() throws SQLException {
        return cashierDAO.generateCashierId();
    }

    @Override
    public boolean cashierLogin(String name, String password) throws SQLException {
        return cashierDAO.cashierLogin(name,password);
    }

    @Override
    public boolean checkNicAndUserNameIsExists(String name, String nic) throws SQLException {
        return cashierDAO.checkNicAndUserNameIsExists(name,nic);
    }

    @Override
    public CashierDTO searchCashier(String cashierId) throws SQLException {
        Cashier search = cashierDAO.search(cashierId);
        return new CashierDTO(search.getId(),search.getName(),search.getNic(),search.getEmail(),search.getPassword());
    }

    @Override
    public boolean ifCashierIdExists(String id) throws SQLException {
       return cashierDAO.ifCashierIdExists(id);
    }
}
