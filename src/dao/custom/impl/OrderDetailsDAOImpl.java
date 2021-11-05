package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean add(OrderDetails orderDetails) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO OrderDetails VALUES(?,?,?,?,?)", orderDetails.getItemCode(), orderDetails.getOrderId(), orderDetails.getOrderQty(), orderDetails.getDiscount(), orderDetails.getPrice());
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws SQLException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM orderDetails");
        ArrayList<OrderDetails>orderDetails = new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetails(rst.getString(1),rst.getString(1),rst.getInt(3),rst.getBigDecimal(4),rst.getBigDecimal(5)));
        }return orderDetails;
    }

    @Override
    public OrderDetails search(String s) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM OrderDetails WHERE orderId=?", s);
        if (rst.next()){
            return new OrderDetails(rst.getString(1),rst.getString(1),rst.getInt(3),rst.getBigDecimal(4),rst.getBigDecimal(5));
        }return null;
    }
}
