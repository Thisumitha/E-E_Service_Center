package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.tm.OrderDetailsTm;
import dto.tm.OrderTm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
    }
}
