package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case MakeAnOrder:
                return new MakeAnOrderBOImpl();
            case CASHIER:
                return new CashierBOImpl();
            case Income:
                return new IncomeBOImpl();
            case RETURNS:
                return new ReturnsBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, MakeAnOrder, CASHIER, Income, RETURNS;
    }
}
