package dao;

import dao.custom.impl.QueryDAOImpl;
import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDao(DAOTypes types) {
        switch (types) {
            case CASHIER:
                return new CashierDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case OrderDetails:
                return new OrderDetailsDAOImpl();
            case Query:return new QueryDAOImpl();
            case Income:return new IncomeDAOImpl();
            case RETURNS:return new ReturnsDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER, CASHIER, ITEM, ORDER, OrderDetails,Query,Income,RETURNS;
    }
}
