package controller;

import bo.BoFactory;
import bo.custom.AccessBo;
import bo.custom.EmployerBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.AccessDto;
import dto.EmployerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminFormController {
    public BorderPane pane;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public CheckBox storeAcess;
    public CheckBox inventoryAcess;
    public CheckBox customerAcess;
    public CheckBox reportAcess;
    public CheckBox repairAcess;

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
}
