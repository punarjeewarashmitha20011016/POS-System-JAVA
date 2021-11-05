package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomDTO> searchOrderDetailsByOrderId(String orderId) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("select o.orderId,od.orderQty,od.discount,od.price,od.itemCode from `order` o inner join orderDetails od on od.orderId=o.orderId where o.orderId=?", orderId);
        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(rst.getString(1), rst.getString(5), rst.getInt(2), rst.getBigDecimal(3), rst.getBigDecimal(4)));
        }
        return customDTOS;
    }

    @Override
    public ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("select c.customerId,c.customerName,o.orderdate,o.orderid,o.cost from customer c inner join `order` o on o.cId=c.customerid");
        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(rst.getString(1), rst.getString(2), LocalDate.parse(rst.getString("orderDate")), rst.getString(4), rst.getBigDecimal(5)));
        }
        return customDTOS;

    }

    @Override
    public ArrayList<CustomDTO> mostAndLeastMovableItem() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery(" SELECT i.itemCode,i.itemDescription,sum(od.orderQty),sum(od.price)" +
                " from item i inner join orderdetails od on i.itemCode=od.itemCode group by itemCode order by orderQty desc");
        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getBigDecimal(4)));
        }
        return customDTOS;
    }

}
