package controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryComponentController implements Initializable {

    public JFXCheckBox checkBox;

    public void setData(String catogery){
        checkBox.setText(catogery);
        checkBox.setId(catogery);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
