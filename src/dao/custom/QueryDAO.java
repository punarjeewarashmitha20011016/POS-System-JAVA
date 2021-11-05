package dao.custom;

import dao.SuperDAO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public ArrayList<CustomDTO> searchOrderDetailsByOrderId(String orderId) throws SQLException;

    public ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException;

    public ArrayList<CustomDTO>mostAndLeastMovableItem() throws SQLException;

}
