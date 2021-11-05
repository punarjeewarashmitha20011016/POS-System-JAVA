package dao.custom;

import dao.CrudDAO;
import entity.Returns;

import java.sql.SQLException;

public interface ReturnsDAO extends CrudDAO<Returns,String> {
    public String generateReturnId() throws SQLException;
}
