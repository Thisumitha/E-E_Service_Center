package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.TypeBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.ItemDto;
import dto.TypeDto;
import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.Double.parseDouble;

public class InventoryFormController {

    public TreeTableColumn colName;
    public TreeTableColumn colUnitPrice;
    public TreeTableColumn colCategory;
    public TreeTableColumn colOption;
    public TreeTableColumn colCode;
    public TreeTableColumn colType;
    public TreeTableColumn colQty;
    public JFXTreeTableView<ItemTm> tblItem;

    public ImageView itemImage;
    public JFXTextField searchText;


    public BorderPane pane;
    public JFXTextField id;
    public JFXTextField name;
    public JFXComboBox type;
    public JFXTextField qty;
    public JFXTextField price;

    private List<String> categories=new ArrayList<>();
    private List<TypeDto> types=new ArrayList<>();
    List<ItemDto>dtoList=new ArrayList<>();
    String imagepath;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private TypeBo typeBo = BoFactory.getInstance().getBo(BoType.TYPE);

    public void initialize() throws ClassNotFoundException, SQLException {


        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        cmbLoad();
        loadItemTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getName().contains(newValue);
                    }
                });
            }
        });
    }

    private void setData(TreeItem<ItemTm> newValue) {
        if (newValue != null) {
            id.setText(newValue.getValue().getCode());
            name.setText(newValue.getValue().getName());
            price.setText(String.valueOf(newValue.getValue().getUnitPrice()));
            qty.setText(String.valueOf(newValue.getValue().getQty()));
            int catIndex;
            int typeIndex;
            catIndex = categories.indexOf( newValue.getValue().getCategory());
            typeIndex = types.indexOf(newValue.getValue().getType());
            type.getSelectionModel().select(typeIndex);
            imagepath=getimage(newValue.getValue().getCode());
            Image image = new Image(imagepath, 120, 127, false, true);
            itemImage.setImage(image);
        }
    }

    private String getimage(String code) {
        for(ItemDto dto:dtoList){
            if (dto.getCode().equals(code)) {

                return dto.getImage();
            }
        }
        return code;
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();


        try {

             dtoList =itemBo.allItems();

            for (ItemDto dto:dtoList){
                JFXButton btn = new JFXButton("Delete");

                ItemTm tm = new ItemTm(
                        dto.getCode(),
                        dto.getName(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        null,
                        dto.getType(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(tm.getCode());
                });

                tmList.add(tm);
            }

            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = itemBo.deleteItem(code);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void cmbLoad() throws ClassNotFoundException, SQLException {
        categories = itemBo.getCategories();
        types= typeBo.allItems();

        ObservableList typelist = FXCollections.observableArrayList();

        for(TypeDto type:types){
            typelist.add(type.getType());
        }
        type.setItems(typelist);

    }

    public void importphoto(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if(file != null) {
            imagepath = file.getPath();
            System.out.println("file:"+imagepath);
            Image image = new Image(imagepath, 120, 127, false, true);
            itemImage.setImage(image);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");

            alert.showAndWait();
        }

    }

    public void updateButtonOnAcction(ActionEvent actionEvent) {


        try {

            boolean isUpdated = itemBo.updateItem(new ItemDto(id.getText(),
                    name.getText(),
                    parseDouble(price.getText()),
                    Integer.parseInt(qty.getText()),
                    type.getValue().toString(),
                    imagepath

            ));

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
                loadItemTable();
                clearFields();
            }

        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveButtonOnAcction(ActionEvent actionEvent) {
        try {



            Boolean isSaved = itemBo.saveItem(new ItemDto(id.getText(),
                    name.getText(),
                    parseDouble(price.getText()),
                    Integer.parseInt(qty.getText()),
                    type.getValue().toString(),
                    imagepath


            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                loadItemTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void clearFields() throws FileNotFoundException {
    try{
            name.clear();
            id.clear();
            price.clear();
            qty.clear();
            type.getSelectionModel().clearSelection();
            itemImage.setImage(null);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    }

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

    public void createButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/typepopup.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Create Type");
            popupStage.setScene(new Scene(root));

            // Set up the controller for the popup (if needed)
            typePopupController typePopupController = loader.getController();

            // Show the popup
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
