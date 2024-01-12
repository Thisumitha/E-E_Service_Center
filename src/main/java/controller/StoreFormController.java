package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.TypeBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.ItemDto;
import dto.TypeDto;
import dto.catelog.ItemCatologDto;
import dto.tm.CatologTm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class StoreFormController  {


    public JFXTreeTableView<CatologTm> tblList;
    public TreeTableColumn colName;
    public TreeTableColumn colMinus;
    public TreeTableColumn colQty;
    public TreeTableColumn colPus;
    public TreeTableColumn colPrice;
    public JFXTextField searchText;
    public Label priceLabel;
    public JFXButton load;



    @FXML
    private BorderPane pane;
    @FXML
    private VBox layout;
    @FXML
    private VBox layout1;
    public GridPane grid ;



    private List<TypeDto> electricalTypes=new ArrayList<>();
    private List<TypeDto> elctronicTypes=new ArrayList<>();


    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private TypeBo typeBo = BoFactory.getInstance().getBo(BoType.TYPE);

    List<ItemCatologDto> items=new ArrayList<>();
    List<TypeDto> typeDtos =new ArrayList<TypeDto>();
    List<ItemDto> itemDtos =new ArrayList<ItemDto>();
    private static List<ItemCatologDto> catologDtoList=new ArrayList<>();
    ObservableList<CatologTm> tmList = FXCollections.observableArrayList();




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



    public void initialize() {
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<CatologTm,String>("name"));
        colMinus.setCellValueFactory(new TreeItemPropertyValueFactory<CatologTm,JFXButton>("btnMinus"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<CatologTm,Integer>("qty"));
        colPus.setCellValueFactory(new TreeItemPropertyValueFactory<CatologTm,JFXButton>("btnPlus"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<CatologTm,Double>("price"));
        try {
            itemDtos = itemBo.allItems();
            typeDtos.addAll(typeBo.allItems());

            loadTypes();
            loadItems(itemDtos);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                loadToCart();
//            }
//        }, 0, 5000);



    }
    public void loadToCart(){
    double tot=0;
        ObservableList<CatologTm> tmList = FXCollections.observableArrayList();



            List<ItemCatologDto> dtoList =catologDtoList;

            for (ItemCatologDto dto:dtoList){
                ImageView imageMinus = new ImageView("/Assets/minus.png");
                imageMinus.setFitWidth(20);
                imageMinus.setFitHeight(20);
                ImageView imagePlus = new ImageView("/Assets/plus.png");
                imagePlus.setFitWidth(20);
                imagePlus.setFitHeight(20);
                JFXButton btnMinus = new JFXButton();
                btnMinus.setGraphic(imageMinus);
                JFXButton btnPlus = new JFXButton();
                btnPlus.setGraphic(imagePlus);
                CatologTm tm = new CatologTm(
                        dto.getCode(),
                        dto.getName(),
                        btnMinus,
                        dto.getQty(),
                        btnPlus,
                        dto.getPrice()
                );

                btnMinus.setOnAction(actionEvent -> {
                    changecart(tm.getId(),"-");

                });
                btnPlus.setOnAction(actionEvent -> {
                    changecart(tm.getId(),"+");
                });

                tmList.add(tm);
                tot+= dto.getPrice();
            }


        TreeItem<CatologTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblList.setRoot(treeItem);
        tblList.setShowRoot(false);



        priceLabel.setText(String.format("%.2f",tot));
    }

    private void loadItems(List<ItemDto> list) throws SQLException, ClassNotFoundException {
        int column = 0;
        int row = 1;



       items=new ArrayList<>(allcatologs(list));
        try {
//            System.out.println(grid.getRowCount());
//
//
//
//            // Create RowConstraints for the row
//            RowConstraints rowConstraints = new RowConstraints();
//            rowConstraints.setMinHeight(10.0);
//            rowConstraints.setPrefHeight(30.0);
//            rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
//
//
//            // Add the RowConstraints to the GridPane
//            grid.getRowConstraints().add(rowConstraints);


            for (int i = 0; i < items.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/itemCatalog.fxml"));

            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            ItemController controller = fxmlLoader.getController();
            controller.setData(items.get(i));

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
        }catch (IOException e) {
                throw new RuntimeException(e);
            }



    }

    private void loadTypes() throws SQLException, ClassNotFoundException {





        for(TypeDto dto:typeDtos){
            if(dto.getCategory().equals("Electronic")){
                elctronicTypes.add(dto);
            } else if (dto.getCategory().equals("Electrical")) {
                electricalTypes.add(dto);
            }
        }

        for (TypeDto type : elctronicTypes) {


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


        for (TypeDto type : electricalTypes) {


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/category.fxml"));
                AnchorPane anchorPane = (AnchorPane)fxmlLoader.load();

                CategoryComponentController categoryComponentController =(CategoryComponentController) fxmlLoader.getController();
                categoryComponentController.setData(type);

                if (layout1 != null) {
                    layout1.getChildren().add(anchorPane);
                } else {

                    System.err.println("Error: 'layout' is not initialized.");
                }
            } catch (IOException | NullPointerException e) {

                e.printStackTrace();
            }
        }

    }
    public List<ItemCatologDto> allcatologs(List<ItemDto> itemscatolog) throws SQLException, ClassNotFoundException {


        ArrayList<ItemCatologDto> list = new ArrayList<>();
        JFXButton btn=new JFXButton() ;
        for (ItemDto dto : itemscatolog) {
            ItemCatologDto itemCatologDto = new ItemCatologDto(
                    dto.getName(),
                    dto.getCode(),
                    dto.getImage(),
                    1,
                    dto.getUnitPrice(),
                    btn

            );
            btn.setOnAction(actionEvent -> {
                loadToCart();
            });
            list.add(itemCatologDto);

        }


        return list;
    }
    public void selectedType(String checkBoxId) throws SQLException, ClassNotFoundException {
        List<ItemDto> list =new ArrayList<>();
        List<ItemDto> dtoList = itemBo.allItems();
        List<TypeDto> dtos = typeBo.allItems();

        for (ItemDto item:dtoList){
            for (TypeDto type:dtos){
                if(item.getType().equals(type.getType())){

                    if(checkBoxId.equals(type.getId())){
                        list.add(item);
                    }
                }
            }

        }

        if (!(list.isEmpty())) {
            loadItems(list);
        }

    }

    public void checkOutButton(ActionEvent actionEvent) {
        if (catologDtoList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cart is Empty");
            alert.show();
        }else {
            itemBo.savecart(catologDtoList);
            Stage stage = (Stage) pane.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
                stage.setResizable(true);
                stage.setTitle("Place Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void reloadButton(ActionEvent actionEvent) {
        loadToCart();

    }
    public void savecart(ItemCatologDto itemCatologDto) {
        boolean add= true;
        if (catologDtoList.isEmpty()){
            catologDtoList.add(itemCatologDto);
        }else {
            for (ItemCatologDto dto : catologDtoList) {
                if (dto.getCode().equals(itemCatologDto.getCode())) {
                    addAgain(itemCatologDto.getCode());

                    add=false;
                }
            }
            if (add){
                catologDtoList.add(itemCatologDto);

            }
        }




    }

    private void addAgain(String code) {
        try {
            List<ItemDto> itemDtos = new ArrayList<>(itemBo.allItems());
            Iterator<ItemCatologDto> iterator = catologDtoList.iterator();

            while (iterator.hasNext()) {
                ItemCatologDto catologDto = iterator.next();

                for (ItemDto dto : itemDtos) {
                    if (code.equals(dto.getCode()) && dto.getCode().equals(catologDto.getCode())) {
                            catologDto.setPrice(catologDto.getPrice() + dto.getUnitPrice());
                            catologDto.setQty(catologDto.getQty() + 1);
                    }
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void changecart(String id, String bool) {
        try {
            List<ItemDto> itemDtos = new ArrayList<>(itemBo.allItems());
            Iterator<ItemCatologDto> iterator = catologDtoList.iterator();

            while (iterator.hasNext()) {
                ItemCatologDto catologDto = iterator.next();

                for (ItemDto dto : itemDtos) {
                    if (id.equals(dto.getCode()) && dto.getCode().equals(catologDto.getCode())) {
                        if ("+".equals(bool)) {
                            catologDto.setPrice(catologDto.getPrice() + dto.getUnitPrice());
                            catologDto.setQty(catologDto.getQty() + 1);

                        } else {
                            if (!(catologDto.getQty() == 1)) {
                                catologDto.setPrice(catologDto.getPrice() - dto.getUnitPrice());
                                catologDto.setQty(catologDto.getQty() - 1);
                            } else {
                                System.out.println(catologDto);
                                iterator.remove();  // Safely remove the item using the iterator
                            }
                        }
                    }
                }
            }
            loadToCart();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void reloadTrack(MouseEvent mouseEvent) {
       load.fire();
    }
}
