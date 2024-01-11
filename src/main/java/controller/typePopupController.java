package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.TypeBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.ItemDto;
import dto.TypeDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class typePopupController {
    public JFXComboBox categoryUpdate;
    public JFXTextField txtnameUpdate;
    public JFXComboBox selectType;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    public JFXTextField txtname;
    public JFXComboBox category;
    private List<String> categories=new ArrayList<>();

    private List<TypeDto> types=new ArrayList<>();
    private TypeBo typeBo = BoFactory.getInstance().getBo(BoType.TYPE);
    private  String selectedText;
    private  String id;


    public void createButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtname.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill Name");
            alert.show();

        } else if (category.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select category");
            alert.show();

        } else if (isNew(txtname.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Already have");
            alert.show();


        } else if (!((category.getValue().toString().isEmpty())&& (txtname.getText().isEmpty()))) {
            String i = typeBo.generateId();
            boolean saveItem = typeBo.saveItem(new TypeDto(
                    txtname.getText(),
                    category.getValue().toString(),
                    i
            ));
            cmbload();
            if (saveItem){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.show();
            }
        }
    }

    private boolean isNew(String text) {
        for (TypeDto type : types){
            if( type.getType().equals(text)) {

                System.out.println(type.getType());
                return true;
            }
        }
        return false;
    }

    public void backButton(ActionEvent actionEvent) {
        txtname.getScene().getWindow().hide();
    }
    public void initialize() throws ClassNotFoundException, SQLException {
        cmbload();
       loadupdate();

    }

    private void loadupdate() {
        selectType.setOnAction(event -> {
            selectedText = selectType.getSelectionModel().getSelectedItem().toString();
            for (TypeDto dto : types) {
                if (dto.getType().equals(selectedText)) {
                    txtnameUpdate.setText(dto.getType());
                    id = dto.getId();

                    for (int i = 0; i < categories.size(); i++) {
                        if (dto.getCategory().equals(categories.get(i))) {
                            categoryUpdate.getSelectionModel().select(i);
                        }
                    }

                    txtnameUpdate.setDisable(false);
                    categoryUpdate.setDisable(false);
                }
            }
        });
    }


    private void cmbload() throws SQLException, ClassNotFoundException {
        categories = itemBo.getCategories();
        types=typeBo.allItems();
        ObservableList categorylist = FXCollections.observableArrayList();
        ObservableList typelist = FXCollections.observableArrayList();
        for(String category:categories){
            categorylist.add(category);
        }
        for(TypeDto typeDto:types){
            typelist .add(typeDto.getType());
        }

        category.setItems(categorylist);
        categoryUpdate.setItems(categorylist);
        selectType.setItems(typelist);
        selectType.setVisibleRowCount(3);

    }

    public void UpdateButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtnameUpdate.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill Name");
            alert.show();

        } else if (categoryUpdate.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select category");
            alert.show();

        } else if (!((categoryUpdate.getValue().toString().isEmpty())&& (txtnameUpdate.getText().isEmpty()))) {


            boolean saveItem = typeBo.updateItem(new TypeDto(
                    txtnameUpdate.getText(),
                    categoryUpdate.getValue().toString(),
                   id
            ));

            if (saveItem){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully update");
                alert.show();
            }
        }
        txtnameUpdate.setDisable(true);
        categoryUpdate.setDisable(true);
    }
}