package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminFormController {
    public BorderPane pane;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public CheckBox storeAcess;
    public CheckBox inventoryAcess;
    public CheckBox customerAcess;
    public CheckBox reportAcess;
    public CheckBox repairAcess;

    public void saveBtn(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty()||txtEmail.getText().isEmpty()){

        }
    }

    public void updateBtn(ActionEvent actionEvent) {
    }

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
}
