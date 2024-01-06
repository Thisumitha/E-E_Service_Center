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
}
