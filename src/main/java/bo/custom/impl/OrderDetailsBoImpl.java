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

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            // Get data source for the report
            JRBeanCollectionDataSource bill = getOrderDetailsReport(id);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, bill);

            // Export the report to a PDF file
            byte[] reportBytes = exportReportToPdfBytes(jasperPrint);

            // Open the generated PDF
            openPdfWithDefaultViewer(reportBytes);

            emailService.sendReciept(email,reportBytes);
        } catch (JRException | SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private byte[] exportReportToPdfBytes(JasperPrint jasperPrint) throws JRException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new JRException("Error exporting report to PDF", e);
        }
    }

    private void openPdfWithDefaultViewer(byte[] pdfBytes) throws IOException {
        File pdfFile = File.createTempFile("invoice", ".pdf");
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(pdfFile)) {
            fos.write(pdfBytes);
        }

        Desktop.getDesktop().open(pdfFile);
    }

}
