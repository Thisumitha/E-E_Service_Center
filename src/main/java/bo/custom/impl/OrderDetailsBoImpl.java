package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dao.util.HibernateUtil;
import dto.OrderDetailsDto;
import entity.OrderDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao= DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);



    @Override
    public void printBill(String oId) {

        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JRBeanCollectionDataSource orderDetailsReport =getOrderDetailsReport(oId);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,orderDetailsReport);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private JRBeanCollectionDataSource getOrderDetailsReport(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetails = orderDetailsDao.getAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);
        return dataSource;
    }
}
