package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import bo.custom.RepairItemBo;
import bo.custom.RepairPartsBo;
import bo.custom.impl.RepairItemBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.RepairItemDto;
import dto.RepairPartDto;
import dto.tm.ItemTm;
import dto.tm.PlaceOrderTm;
import dto.tm.RepairItemTm;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class RepairManagementController implements Initializable {
    public BorderPane pane;
    public JFXTextField searchText;
    public JFXComboBox cmbstetus;
    public JFXTextField txtEmail;

    public JFXTextField txtNumber;
    public JFXTextArea txtMsg;
    public JFXTextField txtName;
    public JFXTextField cusName;
    public JFXTextField txtPartName;
    public JFXTextField txtPartQty;
    public JFXTextField txtPartPrice;
    public JFXTreeTableView<RepairItemTm> tblRepair;
    public TreeTableColumn tblItemCode;
    public TreeTableColumn tblItemName;
    public TreeTableColumn tblItemStetus;

    public DatePicker datePicker;
    public TreeTableColumn tblCustomerName;
    public JFXTreeTableView tblElecParts;
    public TreeTableColumn colRepName;
    public TreeTableColumn colRepPrice;
    public TreeTableColumn colRepQty;


    private RepairItemBo repairItemBo = BoFactory.getInstance().getBo(BoType.REPAIR_ITEM);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private RepairPartsBo repairPartsBo = BoFactory.getInstance().getBo(BoType.REPAIR_PARTS);

    public void btnUpdate(ActionEvent actionEvent) {

    }

    public void btnSave(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!(txtName.getText().isEmpty()||txtMsg.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()||cusName.getText().isEmpty())){
            Customer customer = new Customer(
                    customerBo.generateId(),
                    cusName.getText(),
                    Integer.parseInt(txtNumber.getText()),
                    txtEmail.getText()
            );

            String date = (datePicker.getValue() == null) ? "Processing" : datePicker.getValue().toString();
            repairItemBo.saveItem(new RepairItemDto(
                    repairItemBo.generateId(),
                    txtName.getText(),
                    date,
                    "thisu",
                    "",
                    txtMsg.getText(),
                    customer,
                    null
            ));
        }
        loadTable();

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

    public void elecPartAddButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtPartName.getText().isEmpty()||txtPartQty.getText().isEmpty()||txtPartPrice.getText().isEmpty()){
            boolean saveItem = repairPartsBo.saveItem(new RepairPartDto(
                    repairPartsBo.generateId(), 
                    txtPartName.getText().toString(),
                    Integer.parseInt(txtPartQty.getText().toString()),
                    Double.parseDouble(txtPartPrice.getText().toString())
            ));
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        tblItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        tblItemStetus.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        tblCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("Customer"));
        tblRepair.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            tblRepair(newValue);
        });

        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void tblRepair(TreeItem<RepairItemTm> newValue) {

    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<RepairItemTm> tmList = FXCollections.observableArrayList();
        List<RepairItemDto> repairItemDtos = repairItemBo.allItems();
        for (RepairItemDto dto:repairItemDtos){
            JFXButton btn = new JFXButton();
            if (!(dto.getDate().equals("Processing")||dto.getDate().equals("completed"))) {

                LocalDate date = Date.valueOf(dto.getDate()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                int daysBetween = (int) ChronoUnit.DAYS.between(currentDate, date);


                if (daysBetween > 10) {
                    btn = new JFXButton("    “Pending”      ");
                    btn.setStyle("-fx-background-color:#D93400; -fx-text-fill: white; -fx-font-size: 16px;");
                } else if (daysBetween < 5) {
                    btn = new JFXButton("    “Pending”      ");
                    btn.setStyle("-fx-background-color: #CC0101; -fx-text-fill: white; -fx-font-size: 16px;");
                } else {
                    btn = new JFXButton("      “Not Complete”       ");
                    btn.setStyle("-fx-background-color: #3F3D3D; -fx-text-fill: white; -fx-font-size: 16px;");
                }

            } else if (dto.getDate().equals("Processing")) {
                btn = new JFXButton("      “Processing”       ");

                btn.setStyle("-fx-background-color: #E7C200;-fx-text-fill: white; -fx-font-size: 16px;");
            }else if(dto.getDate().equals("completed")) {
                btn = new JFXButton("      “completed”       ");
                btn.setStyle("-fx-background-color: MediumSeaGreen;-fx-text-fill: white; -fx-font-size: 16px;");

            }




            RepairItemTm tm =new RepairItemTm(
            dto.getId(),
            dto.getName(),
            btn,
            dto.getCustomer().getName(),
            dto.getDate()
            );

            btn.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String string = tm.getStatus().equals("Processing") ? "Processing" : tm.getStatus().equals("completed") ? "completed" : (tm.getStatus() +"  want to give");
                alert.setContentText(string);
                alert.show();
            });

            tmList.add(tm);
        }
        TreeItem<RepairItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblRepair.setRoot(treeItem);
        tblRepair.setShowRoot(false);
    }
}
