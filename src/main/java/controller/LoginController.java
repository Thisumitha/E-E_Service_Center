package controller;

import bo.BoFactory;
import bo.custom.EmployerBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dao.util.EmailService;
import dao.util.User;
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

            for (EmployerDto employerDto : employerDtoList) {
                if (employerDto.getEmail().equals(txtEmail.getText())) {
                    isEmailValid = true;

                    if (employerDto.getPassword().equals(txtPassword.getText())) {
                        user.setData(employerDto);
                        Stage stage = (Stage) pane.getScene().getWindow();
                        try {
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
                            stage.setResizable(true);
                            stage.setTitle("Dashboard");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Incorrect password
                        showAlert("Wrong Password", "Please enter the correct password.");
                    }

                    // Break out of the loop once a matching email is found
                    break;
                }
            }

            // If no matching email is found
            if (!isEmailValid) {
                showAlert("Incorrect Email", "The entered email is not registered.");
            }
        }else {
            showAlert("Missing Information", "Fill Email and Password.");
        }


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
