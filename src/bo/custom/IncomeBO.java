package bo.custom;

import bo.SuperBO;
import dto.IncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeBO extends SuperBO {
    public IncomeDTO dailyIncome() throws SQLException;

    public IncomeDTO monthlyIncome() throws SQLException;

    public IncomeDTO annuallyIncome() throws SQLException;

    public ArrayList<IncomeDTO> getAllIncome() throws SQLException;

    public boolean deductIncome(double income) throws SQLException;

}
