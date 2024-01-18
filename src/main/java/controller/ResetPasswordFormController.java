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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ResetPasswordFormController {

    public BorderPane pane;
    public JFXPasswordField txtOtp;
    public JFXTextField txtEmail;
    EmailService emailService=new EmailService();
    private  String otp=null;
    private EmployerDto employer=null;
    private EmployerBo employerBo = BoFactory.getInstance().getBo(BoType.EMPLOYER);
    User user=new User();

    public void sendOtp(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(txtEmail.getText().isEmpty()){
            showAlert("fill Email", "Email is empty");
        }else{
            List<EmployerDto> employerDtoList = employerBo.allIEmployers();
            boolean isEmailValid = false;

            for (EmployerDto employerDto : employerDtoList) {
                if (employerDto.getEmail().equals(txtEmail.getText())) {
                    employer=employerDto;
                    otp= employerBo.generateOTP();
                    isEmailValid = true;
                    emailService.sendOtpEmail(employerDto.getEmail(), otp);
                    txtOtp.setDisable(false);
                    break;
                }
            }


            if (!isEmailValid) {
                showAlert("Incorrect Email", "The entered email is not registered.");
            }
        }
    }

    public void resetBtnAction(ActionEvent actionEvent) {
        if (txtOtp.getText().isEmpty()){
            showAlert("fill otp", "Otp is empty");
        }else{
            if (txtOtp.getText().equals(otp)){
                user.setData(employer);
               loadProfile();
            }
        }
    }

    private void loadProfile() {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateProfile.fxml"))));
            stage.setResizable(true);
            stage.setTitle("User Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.show();
    }

    public void backButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));

            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
