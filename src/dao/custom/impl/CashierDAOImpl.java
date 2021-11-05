package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CashierDAO;
import entity.Cashier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierDAOImpl implements CashierDAO {
    @Override
    public boolean add(Cashier cashier) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO LoginDetails VALUES(?,?,?,?,md5(?))",cashier.getId(),cashier.getName(),cashier.getNic(),cashier.getEmail(),cashier.getPassword());
    }

    @Override
    public boolean update(Cashier cashier) throws SQLException {
        return CrudUtil.getExecuteUpdate("UPDATE LoginDetails SET name=?,nic=?,email=?,password=md5(?) WHERE id=?",cashier.getName(),cashier.getNic(),cashier.getEmail(),cashier.getPassword(),cashier.getId());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.getExecuteUpdate("DELETE FROM LoginDetails WHERE id=?",s);
    }

    @Override
    public ArrayList<Cashier> getAll() throws SQLException {
        return null;
    }

    @Override
    public Cashier search(String s) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM LoginDetails WHERE id=?", s);
        if (rst.next()){
            return new Cashier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
        }return null;
    }

    @Override
    public String generateCashierId() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT id FROM LoginDetails order by id desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "CA-00"+temp;
            }else if(temp<=99){
                return "CA-0"+temp;
            }else {
                return "CA-"+temp;
            }
        }else {
            return "CA-001";
        }
    }

    @Override
    public boolean cashierLogin(String name,String password) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM LoginDetails WHERE name=? AND password=md5(?)", name, password);
        return rst.next();
    }

    @Override
    public boolean checkNicAndUserNameIsExists(String contactNo, String nic) throws SQLException {
       return CrudUtil.getExecuteQuery("SELECT name,nic from loginDetails where nic=? or name=?",nic,contactNo).next();
    }

    @Override
    public boolean ifCashierIdExists(String id) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT * FROM LoginDetails WHERE id=?", id).next();
    }
}
