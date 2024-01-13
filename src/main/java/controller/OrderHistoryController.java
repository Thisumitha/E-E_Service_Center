package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.TypeDto;
import dto.tm.ItemTm;
import dto.tm.OrderDetailsTm;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryController {

    public TreeTableColumn colItemQty;
    public JFXTreeTableView<OrderDetailsTm> tblItem;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrder;

    @FXML
    private TreeTableColumn<?, ?> colOderID;

    @FXML
    private TreeTableColumn<?, ?> colTotal;

    @FXML
    private TreeTableColumn<?, ?> colDate;

    @FXML
    private TreeTableColumn<?, ?> colCashier;



    @FXML
    private TreeTableColumn<?, ?> colItemId;



    @FXML
    private TreeTableColumn<?, ?> colItemPrice;

    @FXML
    private TreeTableColumn<?, ?> colItemType;

    @FXML
    private JFXTextField searchText;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    List<OrderDto> dtoList=new ArrayList<>();
    List<ItemDto> itemDtos=new ArrayList<>();


    @FXML
    void backButton(ActionEvent event) {
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
    public void initialize() throws ClassNotFoundException, SQLException {
        colOderID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colTotal.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colCashier.setCellValueFactory(new TreeItemPropertyValueFactory<>("cashier"));

        colItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        colItemQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemQty"));
        colItemPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemPrice"));
        colItemType.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemType"));

        dtoList = orderBo.allOrders();
        itemDtos=itemBo.allItems();
        loadOrderTable();
        tblOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(TreeItem<OrderTm> newValue) {
        if (newValue != null) {
            String id = newValue.getValue().getId();
            loadItems(id);

        }
    }

    private void loadItems(String id) {
        ObservableList<OrderDetailsTm> tmList = FXCollections.observableArrayList();


        for (OrderDto dto:dtoList) {
            if (dto.getOrderId().equals(id)) {
                for (OrderDetailsDto dtoList : dto.getList()) {
                    for (ItemDto item : itemDtos) {
                        if (item.getCode().equals(dtoList.getItemCode())) {
                            OrderDetailsTm tm = new OrderDetailsTm(
                                    dtoList.getItemCode(),
                                    dtoList.getQty(),
                                    dtoList.getPrice(),
                                    item.getType()
                            );
                            tmList.add(tm);
                        }
                    }
                }
            }
        }

        TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);

        tblItem.setRoot(treeItem);
        tblItem.setShowRoot(false);

    }

    private void loadOrderTable() {
        ObservableList<OrderTm> tmList = FXCollections.observableArrayList();


        for (OrderDto dto:dtoList){
            double price=0;
            for(OrderDetailsDto dtoList :dto.getList()){
                price+= dtoList.getPrice()* dtoList.getQty();
            }
            JFXButton btn = new JFXButton("Delete");

            OrderTm tm = new OrderTm(
                    dto.getOrderId(),
                    price,
                    dto.getDate(),
                    dto.getCashier()
                    );



            tmList.add(tm);
        }

        TreeItem<OrderTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);

        tblOrder.setRoot(treeItem);
        tblOrder.setShowRoot(false);

    }


}
