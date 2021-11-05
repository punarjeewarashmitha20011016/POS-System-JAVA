package controller;

import bo.BOFactory;
import bo.custom.IncomeBO;
import bo.custom.ItemBO;
import db.DbConnection;
import dto.IncomeDTO;
import dto.ItemDTO;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemReportsFormController {
    public AnchorPane systemReportsPane;
    public AnchorPane childPane;
    private IncomeBO incomeBO = (IncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Income);
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public JasperReport loadJrxml(String location) throws JRException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream(location));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        return jasperReport;
    }

    public void btnCustomerWiseIncome(ActionEvent actionEvent) throws IOException {
        try {
            JasperReport jasperReport = loadJrxml("/view/reports/CustomerWiseIncomeReport.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnMostAndLeastMovableItem(ActionEvent actionEvent) throws IOException {
        try {
            JasperReport jasperReport = loadJrxml("/view/reports/MostAnLeastSellingItems.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void btnDailyMonthlyAnnuallyIncome(ActionEvent actionEvent){
        double totalIncome = 0.0;
        double totalBuyingPriceOfItems = 0.0;
        double expense = 0.0;
        double profit = 0.0;
        HashMap map = new HashMap();
        try {
            IncomeDTO dailyIncome = incomeBO.dailyIncome();
            IncomeDTO monthlyIncome = incomeBO.monthlyIncome();
            IncomeDTO annuallyIncome = incomeBO.annuallyIncome();
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO dto : allItems
            ) {
                totalBuyingPriceOfItems += dto.getBuyingPrice();
            }

            ArrayList<IncomeDTO> allIncome = incomeBO.getAllIncome();
            for (IncomeDTO dto : allIncome
            ) {
                totalIncome += dto.getTotalIncome();
            }
            expense = totalBuyingPriceOfItems;
            profit = totalIncome - expense;

            map.put("salesIncome", totalIncome);
            map.put("totalIncome", totalIncome);
            map.put("daily", dailyIncome.getTotalIncome());
            map.put("monthly", monthlyIncome.getTotalIncome());
            map.put("annually", annuallyIncome.getTotalIncome());
            map.put("Items", totalBuyingPriceOfItems);
            map.put("totalExpenses", expense);
            map.put("profit", profit);

            JasperReport jasperReport = loadJrxml("/view/reports/IncomeReport.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
            setReportToPane(jasperPrint);
        } catch (SQLException | JRException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setReportToPane(JasperPrint jasperPrint) {
        SwingNode swingNode = new SwingNode();
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        JRViewer viewer = new JRViewer(jasperPrint);
        SwingUtilities.invokeLater(() -> swingNode.setContent(viewer));
        childPane.getChildren().add(swingNode);
    }
}
