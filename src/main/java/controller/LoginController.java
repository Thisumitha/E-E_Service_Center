package controller;

import dao.util.EmailService;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

public class LoginController {
    public BorderPane pane;
    EmailService emailService=new EmailService();
    public void LoginBtnAction(ActionEvent actionEvent) {
        emailService.sendOtpEmail("kavijakumuditha12@gmail.com","Badu wada");
    }
}
