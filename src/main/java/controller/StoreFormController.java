package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.TypeBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.ItemDto;
import dto.catelog.ItemCatolog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreFormController implements Initializable {

    public JFXTextField searchText;
    public BorderPane pane;
    public VBox layout;
    public VBox layout1;
    public GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label codeLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private JFXButton btn;

    @FXML


    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private TypeBo typeBo = BoFactory.getInstance().getBo(BoType.TYPE);
    List<String> elctronicTypes=new ArrayList<>();
    List<String> electricalTypes=new ArrayList<>();
    List<ItemCatolog> items=new ArrayList<>();


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadTypes();
            loadItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadItems() throws SQLException, ClassNotFoundException {
        int column = 0;
        int row = 1;
       items=new ArrayList<>(allcatologs());
        try {
        for (int i = 0; i < items.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/itemCatolog.fxml"));

            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            ItemController controller = fxmlLoader.getController();
            controller.setData(items.get(i));
            System.out.println(i);
            if (column == 4) {
                column = 0;
                ++row;
            }

            grid.add(anchorPane, column++, row);
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
            GridPane.setMargin(anchorPane, new Insets(10) );
        }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



    }

    private void loadTypes() throws SQLException, ClassNotFoundException {
//        elctronicTypes.addAll(typeBo.allItems());
//        electricalTypes.addAll(itemBo.getCategories());

        for (String type : elctronicTypes) {
            System.out.println(type);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/category.fxml"));
                AnchorPane anchorPane = (AnchorPane)fxmlLoader.load();

                CategoryComponentController categoryComponentController =(CategoryComponentController) fxmlLoader.getController();
                categoryComponentController.setData(type);

                if (layout != null) {
                    layout.getChildren().add(anchorPane);
                } else {
                    // Handle the case when layout is null
                    System.err.println("Error: 'layout' is not initialized.");
                }
            } catch (IOException | NullPointerException e) {
                // Handle the exception according to your application's requirements
                e.printStackTrace();
            }
        }

        System.out.println("type");
        for (String type : electricalTypes) {
            System.out.println(type);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/category.fxml"));
                AnchorPane anchorPane = (AnchorPane)fxmlLoader.load();

                CategoryComponentController categoryComponentController =(CategoryComponentController) fxmlLoader.getController();
                categoryComponentController.setData(type);

                if (layout1 != null) {
                    layout1.getChildren().add(anchorPane);
                } else {
                    // Handle the case when layout is null
                    System.err.println("Error: 'layout' is not initialized.");
                }
            } catch (IOException | NullPointerException e) {
                // Handle the exception according to your application's requirements
                e.printStackTrace();
            }
        }

    }
    public List<ItemCatolog> allcatologs() throws SQLException, ClassNotFoundException {

        ArrayList<ItemDto> items = new ArrayList<>(itemBo.allItems());
        ArrayList<ItemCatolog> list = new ArrayList<>();

        for (ItemDto dto : items) {
            ItemCatolog itemCatolog = new ItemCatolog(
                    dto.getName(),
                    dto.getCode(),
                    dto.getImage(),
                    dto.getUnitPrice(),
                    btn

            );
//            btn.setOnAction(actionEvent -> {
//                System.out.println(dto);
//            });
            list.add(itemCatolog);

        }

        return list;
    }
}
