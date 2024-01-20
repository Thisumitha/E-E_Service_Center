package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dao.util.EmailService;
import dto.OrderDetailsDto;
import entity.OrderDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao= DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    EmailService emailService=new EmailService();




    private JRBeanCollectionDataSource getOrderDetailsReport(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetails = orderDetailsDao.getlastOrderDetails(id);

        List<OrderDetailsDto> dtoList = new ArrayList<>();
        for (OrderDetail entity:orderDetails) {
            dtoList.add(new OrderDetailsDto(
                    entity.getOrders().getOrderId(),
                    entity.getItem().getCode(),
                    entity.getQty(),
                    entity.getPrice()
            ));
        }
        System.out.println("\n this  "+dtoList+"\n");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dtoList);
        return dataSource;
    }
    public void printbill(String id, String email) throws JRException {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JRBeanCollectionDataSource customerReport = getOrderDetailsReport(id);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,customerReport);
            JasperViewer.viewReport(jasperPrint,false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            byte[] reportBytes = byteArrayOutputStream.toByteArray();
            emailService.sendReciept(email,reportBytes);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
