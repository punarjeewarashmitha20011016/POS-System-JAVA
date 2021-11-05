package bo.custom.impl;

import bo.custom.ReturnsBO;
import dao.DAOFactory;
import dao.custom.ReturnsDAO;
import dto.ReturnsDTO;
import entity.Returns;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnsBOImpl implements ReturnsBO {
    private ReturnsDAO returnsDAO = (ReturnsDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.RETURNS);

    @Override
    public boolean saveReturns(ReturnsDTO dto) throws SQLException {
        return returnsDAO.add(new Returns(dto.getReturnId(), dto.getOrderId(), dto.getCustomerId(), dto.getItemCode(), dto.getItemDescription(), dto.getReturnQty(), dto.getReturnReason(), dto.getTotal()));
    }

    @Override
    public String generateReturnId() throws SQLException {
        return returnsDAO.generateReturnId();
    }

    @Override
    public ArrayList<ReturnsDTO> getAll() throws SQLException {
        ArrayList<Returns> all = returnsDAO.getAll();
        ArrayList<ReturnsDTO>returnsDTOS = new ArrayList<>();
        for (Returns r:all
             ) {
            returnsDTOS.add(new ReturnsDTO(r.getReturnId(),r.getOrderId(),r.getCustomerId(),r.getItemCode(),r.getItemDescription(),r.getReturnQty(),r.getReturnReason(),r.getTotal()));
        }
        return returnsDTOS;
    }
}

