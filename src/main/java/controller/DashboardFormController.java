package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController  {
    public BorderPane pane;

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/InventoryForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Inventory");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StoreButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StoreForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OrderHistoryButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderHistory.fxml"))));
            stage.setResizable(true);
            stage.setTitle("OrderHistory");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void repairManegment(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/RepairManegmentForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Repair Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void customerButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Customer Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Admin Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportFormController(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Report Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
