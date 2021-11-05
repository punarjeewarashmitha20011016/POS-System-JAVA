package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO Item VALUES(?,?,?,?,?,?)", item.getItemCode(), item.getItemDescription(), item.getSize(), item.getQty(),item.getBuyingPrice(), item.getUnitPrice());
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.getExecuteUpdate("UPDATE Item SET itemDescription=?,itemSize=?,itemQty=?,buyingPrice=?,unitPrice=? WHERE itemCode=?", item.getItemDescription(), item.getSize(), item.getQty(),item.getBuyingPrice(), item.getUnitPrice(), item.getItemCode());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.getExecuteUpdate("DELETE FROM Item WHERE itemCode=?", s);
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM ITEM");
        ArrayList<Item>itemArrayList=new ArrayList<>();
        while (rst.next()){
            itemArrayList.add(new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4),rst.getDouble(5),rst.getDouble(6)));
        }return itemArrayList;
    }

    @Override
    public Item search(String s) throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * from Item WHERE itemCode=?", s);
        while (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4),rst.getDouble(5),rst.getDouble(6));
        }return null;
    }

    @Override
    public String generateItemCode() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT itemCode from Item order by itemCode desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "I-00"+temp;
            }else if(temp<=99){
                return "I-0"+temp;
            }else {
                return "I-"+temp;
            }
        }else {
            return "I-001";
        }
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT itemCode from Item");
        ArrayList<String>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(rst.getString(1));
        }return arrayList;
    }

    @Override
    public boolean ifItemExists(String itemCode) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT itemCode from Item where itemCode=?",itemCode).next();
    }

    @Override
    public ArrayList<Item> itemsLessThanFive() throws SQLException {
        ResultSet next = CrudUtil.getExecuteQuery("SELECT * FROM Item where itemQty <=5");
        ArrayList<Item>items=new ArrayList<>();
        while (next.next()){
            items.add(new Item(next.getString(1),next.getString(2),next.getDouble(3),next.getInt(4),next.getDouble(5),next.getDouble(6)));
        }return items;
    }
}
