package bo.custom;

import bo.SuperBO;
import dto.ReturnsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnsBO extends SuperBO {
    public boolean saveReturns(ReturnsDTO dto) throws SQLException;
    public String generateReturnId() throws SQLException;
    public ArrayList<ReturnsDTO>getAll() throws SQLException;
}
