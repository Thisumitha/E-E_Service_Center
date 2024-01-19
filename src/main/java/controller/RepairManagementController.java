package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.RepairItemBo;
import bo.custom.RepairPartsBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dao.util.User;
import dto.CustomerDto;
import dto.RepairItemDto;
import dto.RepairPartDto;
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
   
    public JFXTextField repairItemSearch;
    public JFXComboBox status;
    public Label price;
    private CustomerDto selectedCustomer;
    private boolean isNew=true;
    private RepairItemBo repairItemBo = BoFactory.getInstance().getBo(BoType.REPAIR_ITEM);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private RepairPartsBo repairPartsBo = BoFactory.getInstance().getBo(BoType.REPAIR_PARTS);

    private String codeSelect=null;
    private String dateSelect=null;
    private String elcPartCode=null;
    User user=new User();

    private List<RepairItemDto> repairItemDto=new ArrayList<>();

    public void btnUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        tblRepair.refresh();

        if (!(txtName.getText().isEmpty()||txtMsg.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()||cusName.getText().isEmpty())){


            String date = (datePicker.getValue() == null) ? "Not Given" : datePicker.getValue().toString();
            boolean updated = repairItemBo.updateItem(new RepairItemDto(
                    codeSelect,
                    txtName.getText(),
                    date,
                    dateSelect,
                    status.getValue().toString(),
                    user.getName(),
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
            String date = (datePicker.getValue() == null) ? "Not given" : datePicker.getValue().toString();


            repairItemBo.saveItem(new RepairItemDto(
                    repairItemBo.generateId(),
                    txtName.getText(),
                    date,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                    "Pending",
                    user.getName(),
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
                    txtPartName.getText(),
                    Integer.parseInt(txtPartQty.getText().toString()),
                    Double.parseDouble(txtPartPrice.getText().toString()),
                    codeSelect
            ));
            if (saveItem){
                repaiPartsTable();
            }
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

        ObservableList statusList = FXCollections.observableArrayList();
        statusList.add("Pending");
        status.setItems(statusList);


        tblRepair.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                clear();

                tblElecParts.setDisable(false);
                if (!(newValue == null)) {
                    elcPartCode=newValue.getValue().getItemCode();
                    setDetails(newValue.getValue());
                    repaiPartsTable();
                    unload();
                }



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

        datePicker.setValue(null);
    }

    private void setDetails(RepairItemTm value) throws SQLException, ClassNotFoundException {
        String itemCode = value.getItemCode();
        List<RepairItemDto> repairItemDtos = repairItemBo.allItems();
        for (RepairItemDto dto:repairItemDtos ){
            if (itemCode.equals(dto.getId())){
                codeSelect = dto.getId();
                dateSelect=dto.getOrderDate();
                cusName.setText(dto.getCustomer().getName());
                txtName.setText(dto.getName());
                txtNumber.setText(String.valueOf(dto.getCustomer().getNumber()));
                txtEmail.setText(dto.getCustomer().getEmail());
                txtMsg.setText(dto.getNote());
                cusName.setEditable(false);
                txtEmail.setEditable(false);
                txtNumber.setEditable(false);
                if (!(dto.getEndDate().equals("Not given"))){
                    LocalDate date = Date.valueOf(dto.getEndDate()).toLocalDate();
                    datePicker.setValue(date);
                }else {
                    datePicker.setValue(null);
                }
                if (dto.getStatus().equals("Pending")){
                    ObservableList statusList = FXCollections.observableArrayList();
                    statusList.addAll("Processing","Completed","Closed");
                    status.setItems(statusList);
                } else if (dto.getStatus().equals("Processing")) {
                    ObservableList statusList = FXCollections.observableArrayList();
                    statusList.addAll("Completed","Closed");
                    status.setItems(statusList);
                }else if (dto.getStatus().equals("Completed")) {
                    ObservableList statusList = FXCollections.observableArrayList();
                    statusList.addAll( "Closed");
                    status.setItems(statusList);
                }
                break;

            }
        }
    }

    private void unload()  {

            tblElecParts.setDisable(false);
            txtPartName.setDisable(false);
            txtPartPrice.setDisable(false);
            txtPartQty.setDisable(false);
            repairPartAddbtn.setDisable(false);


    }

    private void repaiPartsTable() throws SQLException, ClassNotFoundException {
        List<RepairPartDto>tableLoadList=new ArrayList<>();

        for (RepairPartDto repairPartDto :repairPartsBo.allItems()) {
            if (repairPartDto.getItemcode().equals(elcPartCode)) {
                tableLoadList.add(repairPartDto);
            }
        }
        ObservableList<RepairPartsTm> tmList = FXCollections.observableArrayList();
        double tot=0;
        for(RepairPartDto dto:tableLoadList){
            RepairPartsTm tm = new RepairPartsTm(
                    dto.getName(),
                    dto.getQty(),
                    dto.getPrice()
            );
            tot+=dto.getPrice();
            tmList.add(tm);


        }
        TreeItem<RepairPartsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblElecParts.setRoot(treeItem);
        tblElecParts.setShowRoot(false);

        price.setText("Total : RS :" +String.format("%.2f",tot));
    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<RepairItemTm> tmList = FXCollections.observableArrayList();
        repairItemDto = repairItemBo.allItems();
        for (RepairItemDto dto:repairItemDto){
            JFXButton btn = new JFXButton();
            if ((dto.getStatus().equals("Pending"))) {

                LocalDate date = Date.valueOf(dto.getOrderDate()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                int daysBetween = (int) ChronoUnit.DAYS.between(currentDate, date);


                if (daysBetween > 5) {
                    btn = new JFXButton("    Pending      ");
                    btn.setStyle("-fx-background-color:#D93400; -fx-text-fill: white; -fx-font-size: 16px;");
                } else if (daysBetween > 10) {
                    btn = new JFXButton("    Pending      ");
                    btn.setStyle("-fx-background-color: #CC0101; -fx-text-fill: white; -fx-font-size: 16px;");
                } else {
                    btn = new JFXButton("       Pending       ");
                    btn.setStyle("-fx-background-color: #fdfdfd; -fx-text-fill: #000000; -fx-font-size: 16px;");
                }

            } else if (dto.getStatus().equals("Processing")) {
                btn = new JFXButton("      Processing       ");
                btn.setStyle("-fx-background-color: #E7C200;-fx-text-fill: white; -fx-font-size: 16px;");
            }else if(dto.getStatus().equals("Completed")) {
                btn = new JFXButton("      completed       ");
                btn.setStyle("-fx-background-color: MediumSeaGreen;-fx-text-fill: white; -fx-font-size: 16px;");
            }else if(dto.getStatus().equals("Closed")) {
                btn = new JFXButton("      Closed       ");
                btn.setStyle("-fx-background-color: #282626;-fx-text-fill: white; -fx-font-size: 16px;");
            }

            RepairItemTm tm =new RepairItemTm(
            dto.getId(),
            dto.getName(),
            btn,
            dto.getCustomer().getName(),
            dto.getEndDate()
            );

            btn.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String string = tm.getStatus().equals("Not given") ? "Not given" :  (tm.getStatus() +"  want to give");
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
