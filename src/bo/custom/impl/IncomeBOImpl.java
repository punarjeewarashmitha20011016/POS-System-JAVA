package bo.custom.impl;

import bo.custom.IncomeBO;
import dao.DAOFactory;
import dao.custom.IncomeDAO;
import dto.IncomeDTO;
import entity.Income;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeBOImpl implements IncomeBO {
    private IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.Income);

    @Override
    public IncomeDTO dailyIncome() throws SQLException {
        Income income = incomeDAO.dailyIncome();
        return new IncomeDTO(income.getDate(), income.getTotalIncome());
    }

    @Override
    public IncomeDTO monthlyIncome() throws SQLException {
        Income income = incomeDAO.monthlyIncome();
        return new IncomeDTO(income.getDate(), income.getTotalIncome());
    }

    @Override
    public IncomeDTO annuallyIncome() throws SQLException {
        Income income = incomeDAO.annuallyIncome();
        return new IncomeDTO(income.getDate(), income.getTotalIncome());
    }

    @Override
    public ArrayList<IncomeDTO> getAllIncome() throws SQLException {
        ArrayList<Income> all = incomeDAO.getAll();
        ArrayList<IncomeDTO> incomeDTOS = new ArrayList<>();
        for (Income i : all
        ) {
            incomeDTOS.add(new IncomeDTO(i.getDate(), i.getTotalIncome()));
        }
        return incomeDTOS;
    }

    @Override
    public boolean deductIncome(double income) throws SQLException {
        return incomeDAO.deductIncome(income);
    }

}
