package controller;

import bo.BoFactory;
import bo.custom.AccessBo;
import bo.custom.EmployerBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.AccessDto;
import dto.EmployerDto;
import dto.tm.EmployerTm;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.beans.AppletInitializer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminFormController {
    public BorderPane pane;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public CheckBox storeAcess;
    public CheckBox inventoryAcess;
    public CheckBox customerAcess;
    public CheckBox reportAcess;
    public CheckBox repairAcess;
    public JFXTreeTableView<EmployerTm> tblEmp;
    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colBtn;
    public TreeTableColumn colEmail;

    private EmployerBo employerBo = BoFactory.getInstance().getBo(BoType.EMPLOYER);
    private AccessBo accessBo = BoFactory.getInstance().getBo(BoType.ACCESS);
    public void saveBtn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtName.getText().isEmpty()||txtEmail.getText().isEmpty()||Selected()){
            String generateId = employerBo.generateId();
            AccessDto accessDto = new AccessDto(
                    accessBo.generateId(),
                    storeAcess.isSelected(),
                    inventoryAcess.isSelected(),
                    customerAcess.isSelected(),
                    reportAcess.isSelected(),
                    repairAcess.isSelected(),
                    generateId
            );


            employerBo.saveEmployer(new EmployerDto(
                    generateId,
                    txtName.getText(),
                    null,
                    txtEmail.getText(),
                    accessDto
            ));
            loadEmTable();

        }

    }

    private boolean Selected() {
        return storeAcess.isSelected()||inventoryAcess.isSelected()||customerAcess.isSelected()||repairAcess.isSelected()||reportAcess.isSelected();
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
    public void initialize() throws SQLException, ClassNotFoundException {

        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colBtn.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadEmTable();
        tblEmp.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                setData(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(TreeItem<EmployerTm> newValue) throws SQLException, ClassNotFoundException {
        String code = newValue.getValue().getCode();
        List<EmployerDto> employerDtoList = employerBo.allIEmployers();
        for (EmployerDto employerDto :employerDtoList){
            if (employerDto.getCode().equals(code)){
                filData(employerDto);
                break;
            }
        }
    }

    private void filData(EmployerDto employerDto) {
        txtName.setText(employerDto.getName());
        txtEmail.setText(employerDto.getEmail());
        storeAcess.setSelected(employerDto.getAccess().isStoreAccess());
        inventoryAcess.setSelected(employerDto.getAccess().isInventoryAccess());
        customerAcess.setSelected(employerDto.getAccess().isCustomerAccess());
        reportAcess.setSelected(employerDto.getAccess().isReportAccess());
        repairAcess.setSelected(employerDto.getAccess().isRepairAccess());
    }

    private void loadEmTable() throws SQLException, ClassNotFoundException {
        List<EmployerDto> employerDtoList = employerBo.allIEmployers();
        ObservableList<EmployerTm> tmList = FXCollections.observableArrayList();

        for (EmployerDto employerDto :employerDtoList){
            ImageView imageDelete = new ImageView("/Assets/delete.png");
            imageDelete.setFitWidth(30);
            imageDelete.setFitHeight(30);
            JFXButton btnDelete = new JFXButton();
            btnDelete.setGraphic(imageDelete);
            EmployerTm tm = new EmployerTm(
                    employerDto.getCode(),
                    employerDto.getName(),
                    employerDto.getEmail(),
                    btnDelete
            );
            btnDelete.setOnAction(actionEvent -> {
                deleteItem(tm.getCode());
            });
            tmList.add(tm);
            clear();
        }
        TreeItem<EmployerTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);

        tblEmp.setRoot(treeItem);
        tblEmp.setShowRoot(false);


    }

    private void clear() {
        txtEmail.clear();
        txtName.clear();
        storeAcess.setSelected(false);
        inventoryAcess.setSelected(false);
        customerAcess.setSelected(false);
        reportAcess.setSelected(false);
        repairAcess.setSelected(false);

    }

    private void deleteItem(String code) {
    }
}
