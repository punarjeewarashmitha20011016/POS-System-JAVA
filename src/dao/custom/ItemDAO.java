package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    public String generateItemCode() throws SQLException;
    public ArrayList<String>getItemCodes() throws SQLException;
    public boolean ifItemExists(String itemCode) throws SQLException;
    public ArrayList<Item> itemsLessThanFive() throws SQLException;
}
