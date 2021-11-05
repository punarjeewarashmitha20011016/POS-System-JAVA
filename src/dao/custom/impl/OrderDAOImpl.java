package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO `ORDER` VALUES(?,?,?,?,?)", order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getOrderTime(), order.getCost());
    }

    @Override
    public boolean update(Order order) throws SQLException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("Not supported Yet");
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM `ORDER`");
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(rst.getString(1), rst.getString(2), LocalDate.parse(rst.getString("orderDate")), rst.getString(4), rst.getBigDecimal(5)));
        }return orders;
    }

    @Override
    public Order search(String s) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM `ORDER` WHERE orderId=?", s);
        if (rst.next()) {
            return new Order(rst.getString(1), rst.getString(2), LocalDate.parse(rst.getString("orderDate")), rst.getString(4), rst.getBigDecimal(5));
        }
        return null;
    }

    @Override
    public String generateOrderId() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT orderId from `Order` order by orderId desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "O-00" + temp;
            } else if (temp <= 99) {
                return "O-0" + temp;
            } else {
                return "O-" + temp;
            }
        } else {
            return "O-001";
        }
    }

    @Override
    public boolean orderExists(String orderId) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT orderId from `order` where orderId=?",orderId).next();
    }
}
