package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.IncomeDAO;
import entity.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeDAOImpl implements IncomeDAO {
    @Override
    public boolean add(entity.Income income) throws SQLException {
        return CrudUtil.getExecuteUpdate("INSERT INTO Income VALUES(?,?)",income.getDate(),income.getTotalIncome());
    }

    @Override
    public Income dailyIncome() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT date_format(dateAsPerIncome,'%d'),sum(totalIncome) from Income group by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome) order by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome)");
        if (rst.next()) {
            return new Income(rst.getString(1), rst.getDouble(2));
        }
        return null;
    }

    @Override
    public Income monthlyIncome() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT date_format(dateAsPerIncome,'%M'),sum(totalIncome) from Income group by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome) order by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome)");
        if (rst.next()) {
            return new Income(rst.getString(1), rst.getDouble(2));
        }
        return null;
    }

    @Override
    public Income annuallyIncome() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT date_format(dateAsPerIncome,'%Y'),sum(totalIncome) from Income group by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome) order by year(dateAsPerIncome),month(dateAsPerIncome),day(dateAsPerIncome)");
        if (rst.next()) {
            return new Income(rst.getString(1), rst.getDouble(2));
        }
        return null;
    }

    @Override
    public boolean updateIncomeInTheSameDate(LocalDate date,double income) throws SQLException {
        return CrudUtil.getExecuteUpdate("Update Income set totalIncome=(totalIncome+"+income+") where dateAsPerIncome=?",date);
    }

    @Override
    public boolean ifIncomeDateExists(LocalDate date) throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT dateAsPerIncome from income where dateAsPerIncome=?",date).next();
    }

    @Override
    public boolean deductIncome(double income) throws SQLException {
        return CrudUtil.getExecuteQuery("Update Income set totalIncome=(totalIncome-"+income+") order by dateAsPerIncome desc limit 1").next();
    }

    @Override
    public boolean ifIncomeExists() throws SQLException {
        return CrudUtil.getExecuteQuery("SELECT * FROM Income").next();
    }

    @Override
    public Income getLastIncomeRecorded() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * from income order by totalIncome desc limit 1");
        if (rst.next()){
            return new Income(LocalDate.parse(rst.getString(1)),rst.getDouble(2));
        }return null;
    }

    @Override
    public boolean update(entity.Income income) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<entity.Income> getAll() throws SQLException {
        ResultSet rst = CrudUtil.getExecuteQuery("SELECT * FROM Income");
        ArrayList<Income>incomes = new ArrayList<>();
        while (rst.next()){
            incomes.add(new Income(rst.getString(1), rst.getDouble(2)));
        }return incomes;
    }

    @Override
    public Income search(String s) throws SQLException {
        return null;
    }
}
