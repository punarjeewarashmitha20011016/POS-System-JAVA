package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReturnsDAO;
import entity.Returns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnsDAOImpl implements ReturnsDAO {
    @Override
    public boolean add(Returns returns) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO Returns VALUES(?,?,?,?,?,?,?,?)",returns.getReturnId(),returns.getOrderId(),returns.getCustomerId(),returns.getItemCode(),returns.getItemDescription(),returns.getReturnQty(),returns.getReturnReason(),returns.getTotal());
    }

    @Override
    public boolean update(Returns returns) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Returns> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM Returns");
        ArrayList<Returns>returns=new ArrayList<>();
        while (rst.next()){
            returns.add(new Returns(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getInt(6),rst.getString(7),rst.getDouble(8)));
        }return returns;
    }

    @Override
    public Returns search(String s) throws SQLException {
        return null;
    }

    @Override
    public String generateReturnId() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT returnId from Returns order by returnId desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "R-00"+temp;
            }else if (temp<=99){
                return "R-0"+temp;
            }else {
                return "R-"+temp;
            }
        }else {
            return "R-001";
        }
    }
}
