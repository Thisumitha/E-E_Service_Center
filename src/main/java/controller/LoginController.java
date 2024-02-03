package controller;

import bo.BoFactory;
import bo.custom.EmployerBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dao.util.EmailService;
import dao.util.User;
import dto.AccessDto;
import dto.EmployerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class LoginController {
    public BorderPane pane;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    EmailService emailService=new EmailService();
    User user=new User();

    private EmployerBo employerBo = BoFactory.getInstance().getBo(BoType.EMPLOYER);
    public void LoginBtnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!(txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty())) {
            List<EmployerDto> employerDtoList = employerBo.allIEmployers();
            boolean isEmailValid = false;
            if ((txtEmail.getText().equals("admin")) && (txtPassword.getText().equals("admin"))) {
                adminGate();

            } else {


                for (EmployerDto employerDto : employerDtoList) {
                    if (employerDto.getEmail().equals(txtEmail.getText())) {
                        isEmailValid = true;

                        if (employerBo.checkPassword(txtPassword.getText(),employerDto.getPassword())) {
                            user.setData(employerDto);
                            success();
                        } else {

                            showAlert("Wrong Password", "Please enter the correct password.");
                        }


                        break;
                    }
                }


                if (!isEmailValid) {
                    showAlert("Incorrect Email", "The entered email is not registered.");
                }

             }
        }else{
            showAlert("Missing Information", "Fill Email and Password.");
        }

    }


    private void success() {
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

    private void adminGate() {
        AccessDto admin = new AccessDto(
                "admin",
                true,
                true,
                true,
                true,
                true,
                true,
                null
        );

        EmployerDto admindto = new EmployerDto(
                "2007",
                "Thisumitha",
                764842246,
                "thiu2006@gmail.com",
                admin,
                "admin"
        );
        user.setData(admindto);
        success();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    public void resetPassword(MouseEvent mouseEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ResetPassword.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
