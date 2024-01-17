package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.TypeDto;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class CustomerFormController {
    public BorderPane pane;
    public JFXTextField customerSearch;
    public JFXTreeTableView<CustomerTm> tblCustomers;
    public TreeTableColumn colCode;
    public TreeTableColumn colName;
    public TreeTableColumn colEmail;
    public TreeTableColumn colNumber;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtNumber;
    List<CustomerDto>dtoList=new ArrayList<>();
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    String code=null;
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

    public void resetCusAction(ActionEvent actionEvent) {
    }

    public void btnUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean updated = customerBo.updateItem(new CustomerDto(
                code,
                txtName.getText(),
                Integer.parseInt(txtNumber.getText()),
                txtEmail.getText()
        ));
        if (updated){
            loadCusTable();
        }
    }
    public void initialize(){

        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("number"));

        customerSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblCustomers.setPredicate(new Predicate<TreeItem<CustomerTm>>() {
                    @Override
                    public boolean test(TreeItem<CustomerTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getName().contains(newValue)||
                                 treeItem.getValue().getEmail().contains(newValue);
                    }
                });
            }
        });

        loadCusTable();
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(TreeItem<CustomerTm> newValue) {
        if (newValue != null) {
            txtName.setText(newValue.getValue().getName());
            txtNumber.setText(String.valueOf(newValue.getValue().getNumber()));
            txtEmail.setText(newValue.getValue().getEmail());
             code = (newValue.getValue().getCode());

        }
    }

    private void loadCusTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();


        try {

            dtoList =customerBo.allICustomers();
            String category=null;
            for (CustomerDto dto:dtoList){



                CustomerTm tm = new CustomerTm(
                        dto.getCode(),
                        dto.getName(),
                        dto.getNumber(),
                        dto.getEmail()
                );


                tmList.add(tm);
            }

            TreeItem<CustomerTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);

            tblCustomers.setRoot(treeItem);
            tblCustomers.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
