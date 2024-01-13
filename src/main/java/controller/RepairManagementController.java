package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import bo.custom.RepairItemBo;
import bo.custom.impl.RepairItemBoImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.RepairItemDto;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepairManagementController {
    public BorderPane pane;
    public JFXTextField searchText;
    public JFXComboBox cmbstetus;
    public JFXTextField txtEmail;

    public JFXTextField txtNumber;
    public JFXTextArea txtMsg;
    public JFXTextField txtName;
    public JFXTextField cusName;
    private RepairItemBo repairItemBo = BoFactory.getInstance().getBo(BoType.REPAIR_ITEM);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

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
            repairItemBo.saveItem(new RepairItemDto(
                    "item1",
                    txtName.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                    "thisu",
                    "",
                    txtMsg.getText(),
                    customer,
                    null
            ));
        }

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
