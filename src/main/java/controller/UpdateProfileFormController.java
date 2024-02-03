package controller;

import bo.BoFactory;
import bo.custom.EmployerBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dao.util.User;
import dto.EmployerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateProfileFormController {
    public BorderPane pane;
    public Label lblName;
    public JFXTextField txtNumber;
    public JFXTextField txtName;

    public JFXPasswordField txtNewPassword;
    public Label lblSideBarName;
    private EmployerBo employerBo = BoFactory.getInstance().getBo(BoType.EMPLOYER);
    User user=new User();

    public void updateDataBtn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!(txtNumber.getText().isEmpty())) {
            if(!(txtName.getText().isEmpty()||txtNumber.getText().isEmpty())){
                EmployerDto employerDto = user.getUser();
                employerBo.updateEmployer(new EmployerDto(
                    employerDto.getCode(),
                        txtName.getText(),
                        Integer.parseInt(txtNumber.getText()),
                        employerDto.getEmail(),
                        employerDto.getAccess(),
                        employerDto.getPassword()
                ));
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fill number");
            alert.setContentText("Fill number correctly");
            alert.show();
        }
    }

    public void updatePwBtn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

            EmployerDto employerDto = user.getUser();
            if (!(txtNewPassword.getText().isEmpty())){
                if (!checkPassword(txtNewPassword.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Password not Strong ");
                    alert.setHeaderText("change password");
                    alert.setContentText("include 8 characters or more, which includes numbers, letters, and symbols");
                    txtNewPassword.clear();
                    alert.show();
                }else {
                    boolean updated = employerBo.updateEmployerPw(new EmployerDto(
                            employerDto.getCode(),
                            null,
                            null,
                            null,
                            null,
                            employerBo.hashPassword(txtNewPassword.getText())
                    ));
                    if (updated) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("password");
                        alert.setHeaderText("updated");
                        alert.setContentText("password update successfully");
                        alert.show();
                    }
                }
            }


    }
    private  boolean checkPassword(String password) {
        // Minimum length of 8 characters
        if (password.length() < 8) {
            return false;
        }

        // At least one digit
        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }

        // At least one uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        // At least one lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        // At least one symbol
        if (!Pattern.compile("[!@#$%^&*()-=_+{};':\",.<>?/]").matcher(password).find()) {
            return false;
        }

        // If all criteria are met, return true
        return true;
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
    public void initialize() {
        String name = user.getName();
        lblName.setText("Welcome, "+name);
        lblSideBarName.setText("Welcome, "+name);
        loadData();
    }

    private void loadData() {
        EmployerDto employerDto = user.getUser();
        txtName.setText(employerDto.getName());
        if (!(employerDto.getNumber() == null)) {
            txtNumber.setText(String.valueOf(employerDto.getNumber()));
        }


    }

}
