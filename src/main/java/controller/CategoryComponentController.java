package controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryComponentController implements Initializable {

    public JFXCheckBox checkBox;
    public AnchorPane layout;

    public void setData(String catogery){
        this.checkBox.setText(catogery);
        this.checkBox.setId(catogery);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
