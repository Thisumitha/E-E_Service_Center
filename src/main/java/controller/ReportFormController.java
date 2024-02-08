package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import dao.util.BoType;
import dao.util.HibernateUtil;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.internal.SessionImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportFormController implements Initializable {
    public BorderPane pane;
    public AreaChart incomeChart;
    public BarChart customerChart;
    public Label lblItems;
    public Label lblcash;
    public Label lblCus;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    public void backButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("DashBoard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<OrderDto> orderDtos = orderBo.allOrders();
            loadIncomeChart(orderDtos);
            customerChart(orderDtos);
            loadCustomerCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerCount() throws SQLException, ClassNotFoundException {
        List<CustomerDto> customerDtos = customerBo.allICustomers();
        int count=customerDtos.size();
        lblCus.setText(String.valueOf(count));
    }

    private void customerChart(List<OrderDto> orderDtos) throws SQLException, ClassNotFoundException {
        XYChart.Series<String, Integer> chart = new XYChart.Series<>();
            customerChart.getData().clear();
        for (int i = 0; i < orderDtos.size(); i++) {
            OrderDto orderDto = orderDtos.get(i);
            int cus = 1;

            for (int j = i + 1; j < orderDtos.size(); j++) {
                if (orderDto.getDate().equals(orderDtos.get(j).getDate())) {
                    cus += 1;
                }
            }


            boolean dateExists = false;
            for (XYChart.Data<String, Integer> data : chart.getData()) {
                if (data.getXValue().equals(orderDto.getDate())) {
                    dateExists = true;
                    break;
                }
            }


            if (!dateExists) {
                chart.getData().add(new XYChart.Data<>(orderDto.getDate(), cus));
            }
        }
        customerChart.getData().add(chart);
    }

    private void loadIncomeChart(List<OrderDto> orderDtos) {
        XYChart.Series chart=new XYChart.Series();
    incomeChart.getData().clear();
    double tot=0;
    int totcus=0;
    int item=0;
    for(OrderDto dto :orderDtos){
        double price = 0;
           for(OrderDetailsDto items :dto.getList()){
               price+=items.getPrice();
               item++;
           }
           chart.getData().add(new XYChart.Data<>(dto.getDate(),price ));
            tot+=price;
            totcus++;
        }
    incomeChart.getData().add(chart);
    lblcash.setText(String.valueOf(tot));
    lblCus.setText(String.valueOf(totcus));
    lblItems.setText(String.valueOf(item));
    }

    public void DailyReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<OrderDto> orderDtos = orderBo.allOrders();
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<OrderDto> dailyOrders = orderDtos.stream()
                .filter(dto -> today.equals(dto.getDate()))
                .collect(Collectors.toList());

        loadIncomeChart(dailyOrders);
        customerChart(dailyOrders);
    }

    public void annualReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<OrderDto> orderDtos = orderBo.allOrders();

        // Get the current month and year
        String currentMonthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));

        List<OrderDto> monthlyOrders = orderDtos.stream()
                .filter(dto -> dto.getDate() != null && dto.getDate().startsWith(currentMonthYear))
                .collect(Collectors.toList());

        loadIncomeChart(monthlyOrders);
        customerChart(monthlyOrders);
    }

    public void monthlyReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<OrderDto> orderDtos = orderBo.allOrders();

        // Get the current month and year
        String currentMonthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<OrderDto> monthlyOrders = orderDtos.stream()
                .filter(dto -> dto.getDate() != null && dto.getDate().startsWith(currentMonthYear))
                .collect(Collectors.toList());

        loadIncomeChart(monthlyOrders);
        customerChart(monthlyOrders);
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            // Use the ClassLoader to load the JRXML file
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("Reports/income.jrxml");

            if (reportStream == null) {
                throw new RuntimeException("Report template not found in the classpath.");
            }

            JasperDesign design = JRXmlLoader.load(reportStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            // Assuming HibernateUtil.getSession() returns a Hibernate Session
            Connection connection = ((SessionImpl) HibernateUtil.getSession()).connection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

            // Export the report to a PDF file
            File pdfFile = File.createTempFile("customerReport", ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            // Open the generated PDF with the default PDF viewer
            Desktop.getDesktop().open(pdfFile);
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
