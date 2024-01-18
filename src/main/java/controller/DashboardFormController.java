package controller;

import com.jfoenix.controls.JFXButton;
import dao.util.User;
import dto.AccessDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController  {
    public BorderPane pane;
    public JFXButton storeBtn;
    public JFXButton orderHistoryBtn;
    public JFXButton inventoryBtn;
    public JFXButton customerBtn;
    public JFXButton repairBtn;
    public JFXButton reportBtn;
    public JFXButton adminBtn;

    User user=new User();
    public void initialize() {
        getAccess();
    }

    private void getAccess() {
        AccessDto accessLevel = user.getAccessLevel();
        storeBtn.setDisable(!(accessLevel.isStoreAccess()));
        orderHistoryBtn.setDisable(!(accessLevel.isStoreAccess()));
        inventoryBtn.setDisable(!(accessLevel.isInventoryAccess()));
        customerBtn.setDisable(!(accessLevel.isCustomerAccess()));
        repairBtn.setDisable(!(accessLevel.isRepairAccess()));
        reportBtn.setDisable(!(accessLevel.isReportAccess()));
        adminBtn.setDisable(!(accessLevel.isAdminAccess()));
    }

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

    public void signOutBtn(ActionEvent actionEvent) {
        user.setData(null);
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));

            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProfileBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateProfile.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Profile ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
