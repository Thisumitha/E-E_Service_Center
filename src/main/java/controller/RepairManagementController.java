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
import dto.CustomerDto;
import dto.RepairItemDto;
import dto.RepairPartDto;
import dto.TypeDto;
import dto.tm.ItemTm;
import dto.tm.PlaceOrderTm;
import dto.tm.RepairItemTm;
import dto.tm.RepairPartsTm;
import entity.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class RepairManagementController implements Initializable {
    public BorderPane pane;
    public JFXTextField searchText;
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
    public JFXTreeTableView<RepairPartsTm> tblElecParts;
    public TreeTableColumn colRepName;
    public TreeTableColumn colRepPrice;
    public TreeTableColumn colRepQty;
    public JFXButton repairPartAddbtn;
    public Label status;
    public JFXTextField repairItemSearch;
    private CustomerDto selectedCustomer;
    private boolean isNew=true;



    private RepairItemBo repairItemBo = BoFactory.getInstance().getBo(BoType.REPAIR_ITEM);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private RepairPartsBo repairPartsBo = BoFactory.getInstance().getBo(BoType.REPAIR_PARTS);

    private String codeSelect=null;

    private List<RepairItemDto> repairItemDto=new ArrayList<>();

    public void btnUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!(txtName.getText().isEmpty()||txtMsg.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()||cusName.getText().isEmpty())){


            String date = (datePicker.getValue() == null) ? "Processing" : datePicker.getValue().toString();
            boolean updated = repairItemBo.updateItem(new RepairItemDto(
                    codeSelect,
                    txtName.getText(),
                    date,
                    "thisu",
                    "",
                    txtMsg.getText(),
                    null,
                    null
            ));
            if (updated) {

                loadTable();
            }
        }


    }

    public void btnSave(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!(txtName.getText().isEmpty()||txtMsg.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()||cusName.getText().isEmpty())){
            Customer customer = null;
            if ((isNew)) {
                String generateId = customerBo.generateId();

                customerBo.saveCustomer( new CustomerDto(
                        generateId,
                      cusName.getText(),
                      Integer.parseInt(txtNumber.getText()),
                      txtEmail.getText()
              ));
               customer=new Customer(
                       generateId,
                       cusName.getText(),
                       Integer.parseInt(txtNumber.getText()),
                       txtEmail.getText()
               );
          }else{
               customer = new Customer(selectedCustomer.getCode(), selectedCustomer.getName(), selectedCustomer.getNumber(), selectedCustomer.getEmail());
          }
            String date = (datePicker.getValue() == null) ? "Processing" : datePicker.getValue().toString();
            System.out.println(customer);
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
            clear();
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
        if (!(txtPartName.getText().isEmpty()||txtPartQty.getText().isEmpty()||txtPartPrice.getText().isEmpty())){

            boolean saveItem = repairPartsBo.saveItem(new RepairPartDto(
                    repairPartsBo.generateId(),
                    txtPartName.getText().toString(),
                    Integer.parseInt(txtPartQty.getText().toString()),
                    Double.parseDouble(txtPartPrice.getText().toString()),
                    codeSelect
            ));
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        tblItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        tblItemStetus.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        tblCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("Customer"));



        colRepName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colRepPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        colRepQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));


        tblRepair.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                clear();
                tblRepair(newValue);
                setDetails(newValue.getValue().getItemCode());


            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        try {

            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        repairItemSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblRepair.setPredicate(new Predicate<TreeItem<RepairItemTm>>() {
                    @Override
                    public boolean test(TreeItem<RepairItemTm> treeItem) {
                        return treeItem.getValue().getItemCode().contains(newValue) ||
                                treeItem.getValue().getCustomer().contains(newValue)|| treeItem.getValue().getItemName().contains(newValue);
                    }
                });
            }
        });
    }

    private void clear() {
        txtName.clear();
        cusName.clear();
        txtNumber.clear();
        txtEmail.clear();
        txtMsg.clear();
        status.setText("");
        datePicker.setValue(null);
    }

    private void setDetails(String itemCode) throws SQLException, ClassNotFoundException {
        List<RepairItemDto> repairItemDtos = repairItemBo.allItems();
        for (RepairItemDto dto:repairItemDtos ){
            if (itemCode.equals(dto.getId())){
                codeSelect = dto.getId();
                cusName.setText(dto.getCustomer().getName());
                txtName.setText(dto.getName());
                txtNumber.setText(String.valueOf(dto.getCustomer().getNumber()));
                txtEmail.setText(dto.getCustomer().getEmail());
                txtMsg.setText(dto.getNote());
                cusName.setEditable(false);
                txtEmail.setEditable(false);
                txtNumber.setEditable(false);

                if(!(dto.getDate().equals("Processing")||dto.getDate().equals("completed")||dto.getDate().equals("completed"))){
                    LocalDate localDate = LocalDate.parse(dto.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    status.setDisable(true);
                    datePicker.setValue(localDate);
                } else if (dto.getDate().equals("Processing")) {
                    status.setDisable(false);
                    status.setText("Processing");
                }else{
                    status.setDisable(false);
                    status.setText("completed");
                }
            }
        }
    }

    private void tblRepair(TreeItem<RepairItemTm> newValue) throws SQLException, ClassNotFoundException {
        if (newValue != null) {
            tblElecParts.setDisable(true);
            txtPartName.setDisable(false);
            txtPartPrice.setDisable(false);
            txtPartQty.setDisable(false);
            repairPartAddbtn.setDisable(false);
            List<RepairPartDto>tableLoadList=new ArrayList<>();
            for (RepairPartDto repairPartDto :repairPartsBo.allItems()) {
                if (repairPartDto.getItemcode().equals(newValue.getValue().getItemCode())) {

                    tableLoadList.add(repairPartDto);
                }
            }
            if(!(tableLoadList.isEmpty())) {
                tblElecParts.setDisable(false);
                repaiPartsTable(tableLoadList);
            }

        }

    }

    private void repaiPartsTable(List<RepairPartDto> tableLoadList) {
        ObservableList<RepairPartsTm> tmList = FXCollections.observableArrayList();
        for(RepairPartDto dto:tableLoadList){
            RepairPartsTm tm = new RepairPartsTm(
                    dto.getName(),
                    dto.getQty(),
                    dto.getPrice()
            );
            tmList.add(tm);


        }
        TreeItem<RepairPartsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblElecParts.setRoot(treeItem);
        tblElecParts.setShowRoot(false);


    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<RepairItemTm> tmList = FXCollections.observableArrayList();
        repairItemDto = repairItemBo.allItems();
        for (RepairItemDto dto:repairItemDto){
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

    public void resetCusAction(ActionEvent actionEvent) {
        clear();
        cusName.setEditable(true);
        txtEmail.setEditable(true);
        txtNumber.setEditable(true);

    }

    public void SearchedCustomer(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String customer = searchText.getText();
        if (customer.isEmpty() || customer.isBlank() || customer == null) {
            txtName.setText("");
            txtNumber.setText("");
            txtEmail.setText("");
        } else {
            fillCustomerDetails(customer);

        }
    }

    private void fillCustomerDetails(String customer) throws SQLException, ClassNotFoundException {
        for (CustomerDto customerDto: customerBo.allICustomers()){
            if (customerDto.getCode().equals(customer)||customerDto.getName().equals(customer)||String.valueOf(customerDto.getNumber()).equals(customer)||customerDto.getEmail().equals(customer)){
                selectedCustomer=customerDto;
                cusName.setText(customerDto.getName());
                txtNumber.setText(String.valueOf(customerDto.getNumber()));
                txtEmail.setText(customerDto.getEmail());
                isNew=false;
            }else{
                isNew=true;
                cusName.setText("");
                txtNumber.setText("");
                txtEmail.setText("");

            }

        }
    }
}
