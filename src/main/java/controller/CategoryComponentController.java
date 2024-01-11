package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXCheckBox;
import dao.util.BoType;
import dto.TypeDto;
import dto.catelog.ItemCatologDto;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryComponentController implements Initializable {
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    StoreFormController storeFormController= new StoreFormController();
    public JFXCheckBox checkBox;
    public AnchorPane layout;

    public void setData(TypeDto type){
        this.checkBox.setText(type.getType());
        this.checkBox.setId(type.getId());
        checkBox.setOnAction(event -> handleCheckBoxAction(checkBox));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void handleCheckBoxAction(JFXCheckBox checkBox) {

        String checkBoxId = checkBox.getId();


        try {
            storeFormController.selectedType(checkBoxId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
