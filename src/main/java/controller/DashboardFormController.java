package controller;

import bo.BoFactory;
import bo.custom.*;
import com.jfoenix.controls.JFXButton;
import dao.util.BoType;
import dao.util.User;
import dto.AccessDto;
import dto.EmployerDto;
import dto.ItemDto;
import dto.RepairItemDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardFormController  {
    public BorderPane pane;
    public JFXButton storeBtn;
    public JFXButton orderHistoryBtn;
    public JFXButton inventoryBtn;
    public JFXButton customerBtn;
    public JFXButton repairBtn;
    public JFXButton reportBtn;
    public JFXButton adminBtn;
    public Label lblWorkers;

    public Label lblItems;
    public Label lblWorks;

    User user=new User();
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private OrderDetailsBo detailsBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private RepairItemBo repairItemBo = BoFactory.getInstance().getBo(BoType.REPAIR_ITEM);
    private EmployerBo employerBo = BoFactory.getInstance().getBo(BoType.EMPLOYER);
    public void initialize() throws SQLException, ClassNotFoundException {
        getAccess();
        loadRepairCount();
        loadWorkers();
        loadItems();
    }

    private void loadItems() throws SQLException, ClassNotFoundException {
        List<ItemDto> dtoList = itemBo.allItems();
        lblItems.setText(String.valueOf(dtoList.size()));
    }

    private void loadWorkers() throws SQLException, ClassNotFoundException {
        List<EmployerDto> employerDtoList = employerBo.allIEmployers();
        lblWorkers.setText(String.valueOf(employerDtoList.size()));
    }

    private void loadRepairCount() throws SQLException, ClassNotFoundException {
        List<RepairItemDto> repairItemDtos = repairItemBo.allItems();
        int count=0;
        for (RepairItemDto repairItemDto:repairItemDtos){
            if (!(repairItemDto.getStatus().equals("Completed")||repairItemDto.getStatus().equals("Closed"))){
                count++;
            }
        }
        lblWorks.setText(String.valueOf(count));
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
            stage.centerOnScreen();
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
            stage.centerOnScreen();

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
            stage.centerOnScreen();

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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
            stage.setTitle("Profile ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
