package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.ItemDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static java.lang.Double.parseDouble;

public class InventoryFormController {
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    public BorderPane pane;
    public JFXTextField id;
    public JFXTextField name;
    public JFXComboBox type;
    public JFXTextField qty;
    public JFXTextField price;
    public JFXComboBox category;

    public void importphoto(ActionEvent actionEvent) {

    }

    public void updateButtonOnAcction(ActionEvent actionEvent) {

    }

    public void saveButtonOnAcction(ActionEvent actionEvent) {
        try {



            Boolean isSaved = itemBo.saveItem(new ItemDto(id.getText(),
                    name.getText(),
                    parseDouble(price.getText()),
                    Integer.parseInt(qty.getText()),
                    null
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
             //   loadItemTable();
             //   clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
