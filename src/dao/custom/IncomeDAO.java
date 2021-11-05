package dao.custom;

import dao.CrudDAO;
import entity.Income;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IncomeDAO extends CrudDAO<Income, String> {
    public Income dailyIncome() throws SQLException;
    public Income monthlyIncome() throws SQLException;
    public Income annuallyIncome() throws SQLException;
    public boolean updateIncomeInTheSameDate(LocalDate date,double income) throws SQLException;
    public boolean ifIncomeDateExists(LocalDate date) throws SQLException;
    public boolean deductIncome(double income) throws SQLException;
    public boolean ifIncomeExists() throws SQLException;
    public Income getLastIncomeRecorded() throws SQLException;
}
