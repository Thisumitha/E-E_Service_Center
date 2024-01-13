package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

public class RepairManagementController {
    public BorderPane pane;
    public JFXTextField searchText;
    public JFXComboBox cmbstetus;
    public JFXTextField txtEmail;

    public JFXTextField txtNumber;
    public JFXTextArea txtMsg;
    public JFXTextField txtName;

    public void btnUpdate(ActionEvent actionEvent) {

    }

    public void btnSave(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty()||txtMsg.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()){


        }

    }
}
