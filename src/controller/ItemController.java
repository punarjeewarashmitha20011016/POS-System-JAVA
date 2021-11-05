package controller;

import db.DbConnection;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {
    public ArrayList<Item> getItemDetails() throws SQLException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ArrayList<Item>itemArrayList=new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            itemArrayList.add(new Item(resultSet.getString(1),resultSet.getString(2),Double.parseDouble(resultSet.getString(3)),Integer.parseInt(resultSet.getString(4)),resultSet.getDouble(5),resultSet.getDouble(6)));
        }
        return itemArrayList;
    }}
