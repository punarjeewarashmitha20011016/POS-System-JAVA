package bo.custom.impl;

import bo.custom.MakeAnOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.*;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MakeAnOrderBOImpl implements MakeAnOrderBO {
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER);
    private OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.OrderDetails);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
    private QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.Query);
    private IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.Income);

    @Override

    public boolean purchaseOrder(OrderDTO dto) throws SQLException {
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        if (orderDAO.orderExists(dto.getOrderId())) {
            return false;
        }
        Order order = new Order(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost());
        connection.setAutoCommit(false);
        if (!orderDAO.add(order)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (ItemDetailsDTO itemDetailsDTO : dto.getItems()
        ) {
            ItemDetails itemDetails = new ItemDetails(itemDetailsDTO.getItemCode(), itemDetailsDTO.getItemQtyOnHand(), itemDetailsDTO.getUnitPrice(), itemDetailsDTO.getDiscountPrice());
            OrderDetails orderDetails = new OrderDetails(itemDetails.getItemCode(), dto.getOrderId(), itemDetails.getItemQtyOnHand(), itemDetails.getDiscountPrice(), itemDetails.getUnitPrice());
            boolean add = orderDetailsDAO.add(orderDetails);
            if (!add) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            Item search = itemDAO.search(itemDetails.getItemCode());
            search.setQty(search.getQty() - itemDetails.getItemQtyOnHand());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public String generateOrderId() throws SQLException {
        return orderDAO.generateOrderId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        return customerDAO.getCustomerIds();
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException {
        return itemDAO.getItemCodes();
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        Customer search = customerDAO.search(id);
        return new CustomerDTO(search.getCustomerId(), search.getCustomerName(), search.getCustomerAddress(), search.getCustomerNic(), search.getCustomerCity());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException {
        Item search = itemDAO.search(code);
        return new ItemDTO(search.getItemCode(), search.getItemDescription(), search.getSize(), search.getQty(), search.getBuyingPrice(), search.getUnitPrice());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException {
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsDTO> allOrderDetails = new ArrayList<>();
        for (OrderDetails details : all
        ) {
            allOrderDetails.add(new OrderDetailsDTO(details.getItemCode(), details.getOrderId(), details.getOrderQty(), details.getDiscount(), details.getPrice()));
        }
        return allOrderDetails;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException {
        ArrayList<Order> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        for (Order details : all
        ) {
            allOrders.add(new OrderDTO(details.getOrderId(), details.getCustomerId(), details.getOrderDate(), details.getOrderTime(), details.getCost()));
        }
        return allOrders;
    }

    @Override
    public ArrayList<OrderDetailsDTO> searchOrderDetails(String orderId) throws SQLException {
        ArrayList<CustomDTO> customDTOS = queryDAO.searchOrderDetailsByOrderId(orderId);
        ArrayList<OrderDetailsDTO> orderDetailsDTO = new ArrayList<>();
        for (CustomDTO dto : customDTOS
        ) {
            orderDetailsDTO.add(new OrderDetailsDTO(dto.getItemCode(), dto.getOrderId(), dto.getOrderQty(), dto.getDiscount(), dto.getPrice()));
        }
        return orderDetailsDTO;
    }

    @Override
    public OrderDTO searchOrder(String orderId) throws SQLException {
        Order search = orderDAO.search(orderId);
        return new OrderDTO(search.getOrderId(), search.getCustomerId(), search.getOrderDate(), search.getOrderTime(), search.getCost());
    }

    @Override
    public boolean orderExists(String orderId) throws SQLException {
        return orderDAO.orderExists(orderId);
    }

    @Override
    public boolean ifIncomeExists() throws SQLException {
        return incomeDAO.ifIncomeExists();
    }

    @Override
    public boolean ifIncomeDateExists(LocalDate date) throws SQLException {
        return incomeDAO.ifIncomeDateExists(date);
    }

    @Override
    public boolean updateIncomeInTheSameDate(LocalDate date, double income) throws SQLException {
        return incomeDAO.updateIncomeInTheSameDate(date,income);
    }

    @Override
    public boolean saveIncome(IncomeDTO dto) throws SQLException {
        return incomeDAO.add(new Income(dto.getDate(),dto.getTotalIncome()));
    }

    @Override
    public IncomeDTO getLastIncomeRecorded() throws SQLException {
        Income lastIncomeRecorded = incomeDAO.getLastIncomeRecorded();
        return new IncomeDTO(lastIncomeRecorded.getDate(),lastIncomeRecorded.getTotalIncome());
    }
}
