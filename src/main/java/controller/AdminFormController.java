package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import dao.util.BoType;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {
    public BorderPane pane;
    public AreaChart incomeChart;
    public BarChart customerChart;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void customerChart(List<OrderDto> orderDtos) throws SQLException, ClassNotFoundException {
        XYChart.Series<String, Integer> chart = new XYChart.Series<>();

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
    for(OrderDto dto :orderDtos){
        double price = 0;
           for(OrderDetailsDto items :dto.getList()){
               price+=items.getPrice();
           }
           chart.getData().add(new XYChart.Data<>(dto.getDate(),price ));

        }
    incomeChart.getData().add(chart);
    }

}
