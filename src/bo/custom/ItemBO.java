package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public boolean addItem(ItemDTO i) throws SQLException;
    public boolean updateItem(ItemDTO i) throws SQLException;
    public boolean deleteItem(String s) throws SQLException;
    public ItemDTO searchItem(String s) throws SQLException;
    public ArrayList<ItemDTO>getAllItems() throws SQLException;
    public ArrayList<String>getItemCodes() throws SQLException;
    public String generateItemCode() throws SQLException;
    public boolean ifItemExists(String itemCode) throws SQLException;
    public ArrayList<ItemDTO> itemsLessThanFive() throws SQLException;
}
