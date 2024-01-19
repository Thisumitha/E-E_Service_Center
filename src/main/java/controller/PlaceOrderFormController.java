package controller;

import bo.BoFactory;
import bo.custom.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dao.util.HibernateUtil;
import dao.util.User;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.catelog.ItemCatologDto;
import dto.tm.CatologTm;
import dto.tm.PlaceOrderTm;
import entity.Customer;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.internal.SessionImpl;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class PlaceOrderFormController {
    public TreeTableColumn colName;
    public TreeTableColumn colUnitPrice;
    public TreeTableColumn colMinus;
    public TreeTableColumn colQty;
    public TreeTableColumn colPlus;
    public TreeTableColumn colPrice;
    public TreeTableColumn colOption;
    public JFXTreeTableView<PlaceOrderTm> tblPlace;
    public Label priceLabel;
    public JFXTextField discount;
    public Label discountToatal;
    public Label netTotalLabel;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private OrderDetailsBo detailsBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private JFXTextField searchText;
    List<ItemCatologDto> itemCatologDtos=new ArrayList<>();
    List<CustomerDto> customerDtos=new ArrayList<>();
    private double tot=0;
    private double discountedAmount=0;
    private double netTotal=0;
    private CustomerDto selectedCustomer;
    private boolean isNew=true;

    String oId=null;
    User user =new User();
    public void PlaceOrderButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtName.getText().isEmpty()||txtNumber.getText().isEmpty()||txtEmail.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill Customer Details");
            alert.show();
        }else if(netTotal == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Items");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Order");
            alert.setHeaderText("Place Order");
            alert.setContentText("Do you really want to Place Order");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
               placeOrder();
            }else{
                alert.hide();
            }

        }


    }
    private void placeOrder() throws SQLException, ClassNotFoundException {
        String cId= customerBo.generateId();
         oId = orderBo.generateId();
        List<OrderDetailsDto> list = new ArrayList<>();
        for(ItemCatologDto catologDto :itemCatologDtos){
            OrderDetailsDto dto=new OrderDetailsDto(
                    oId,
                    catologDto.getCode(),
                    catologDto.getQty(),
                    catologDto.getPrice()
            );
            list.add(dto);

        }

        if (isNew) {
                    customerBo.saveCustomer(new CustomerDto(
                            cId,
                    txtName.getText(),
                    Integer.parseInt(txtNumber.getText()),
                    txtEmail.getText()
            ));

        }else{
                cId = selectedCustomer.getCode();


        }


            boolean savedOreder = orderBo.saveOreder(new OrderDto(
                    oId,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    cId,
                    list,
                    user.getName()
            ));
            itemBo.updatequantities(itemCatologDtos);
            itemBo.savecart(new ArrayList<>());
            loadTable();
            if (savedOreder){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Order Placed");
                alert.show();

            }



    }
    public void initialize() throws ClassNotFoundException, SQLException, IOException {

        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colMinus.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnMinus"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colPlus.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnPlus"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("option"));
        loadTable();
        loadCustomers();

    }



    private void loadCustomers() throws IOException, SQLException, ClassNotFoundException {
        customerDtos = customerBo.allICustomers();
    }

    private void loadTable() {
         itemCatologDtos = itemBo.loadCart();

        ObservableList<PlaceOrderTm> tmList = FXCollections.observableArrayList();

        List<ItemCatologDto> dtoList =itemCatologDtos;


        for (ItemCatologDto dto:dtoList){
            ImageView imageMinus = new ImageView("/Assets/minus.png");
            imageMinus.setFitWidth(30);
            imageMinus.setFitHeight(30);
            ImageView imagePlus = new ImageView("/Assets/plus.png");
            imagePlus.setFitWidth(30);
            imagePlus.setFitHeight(30);
            ImageView imageDelete = new ImageView("/Assets/delete.png");
            imageDelete.setFitWidth(30);
            imageDelete.setFitHeight(30);
            JFXButton btnMinus = new JFXButton();
            btnMinus.setGraphic(imageMinus);
            JFXButton btnPlus = new JFXButton();
            btnPlus.setGraphic(imagePlus);
            JFXButton btnDelete = new JFXButton();
            btnDelete.setGraphic(imageDelete);

            PlaceOrderTm tm = new PlaceOrderTm(
                    dto.getCode(),
                    dto.getName(),
                    dto.getPrice(),
                    btnMinus,
                    dto.getQty(),
                    btnPlus,
                    dto.getPrice(),
                    btnDelete
            );

            btnMinus.setOnAction(actionEvent -> {
                changecart(tm.getCode(),"-");

            });
            btnPlus.setOnAction(actionEvent -> {
                changecart(tm.getCode(),"+");
            });
            btnDelete.setOnAction(actionEvent -> {
                deleteitem(tm);
            });

            tmList.add(tm);
            tot+= dto.getPrice();
        }


        TreeItem<PlaceOrderTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblPlace.setRoot(treeItem);
        tblPlace.setShowRoot(false);


        priceLabel.setText(String.format("%.2f",tot));
        netTotalLabel.setText(String.format("%.2f",tot));
        netTotal=tot;

    }

    private void deleteitem(PlaceOrderTm tm) {
        Iterator<ItemCatologDto> iterator = itemCatologDtos.iterator();
        while (iterator.hasNext()) {
            ItemCatologDto catologDto = iterator.next();
            if (tm.getCode().equals(catologDto.getCode())){
                iterator.remove();
            }

        }
        loadTable();
    }

    private void changecart(String id, String bool) {
        try {
            List<ItemDto> itemDtos = new ArrayList<>(itemBo.allItems());
            Iterator<ItemCatologDto> iterator = itemCatologDtos.iterator();

            while (iterator.hasNext()) {
                ItemCatologDto catologDto = iterator.next();

                for (ItemDto dto : itemDtos) {
                    if (id.equals(dto.getCode()) && dto.getCode().equals(catologDto.getCode())) {
                        if ("+".equals(bool)) {
                            if (!(dto.getQty() == catologDto.getQty())) {
                                catologDto.setPrice(catologDto.getPrice() + dto.getUnitPrice());
                                catologDto.setQty(catologDto.getQty() + 1);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setContentText("Not Available Qty");
                                alert.show();

                            }
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

            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void discountAddButton(ActionEvent actionEvent) {
        double text = Integer.parseInt(discount.getText());
         netTotal=  tot - text;
        discountToatal.setText(String.format("%.2f",text));
        netTotalLabel.setText(String.format("%.2f",netTotal));
    }

    public void backButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StoreForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchCustomerAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String customer = searchText.getText();
        if (customer.isEmpty() || customer.isBlank() || customer == null) {
            txtName.setText("");
            txtNumber.setText("");
            txtEmail.setText("");
        }else{
            fillCustomerDetails(customer);
        }
    }

    private void fillCustomerDetails(String customer) throws SQLException, ClassNotFoundException {
        for (CustomerDto customerDto: customerDtos){
            if (customerDto.getCode().equals(customer)||customerDto.getName().equals(customer)||String.valueOf(customerDto.getNumber()).equals(customer)||customerDto.getEmail().equals(customer)){
                selectedCustomer=customerDto;
                txtName.setText(customerDto.getName());
                txtNumber.setText(String.valueOf(customerDto.getNumber()));
                txtEmail.setText(customerDto.getEmail());
                txtName.setEditable(true);
                txtEmail.setEditable(true);
                txtNumber.setEditable(true);
                isNew=false;
                break;
            }else{
                selectedCustomer=null;
                txtName.setEditable(false);
                txtEmail.setEditable(false);
                txtNumber.setEditable(false);
                txtName.setText("");
                txtNumber.setText("");
                txtEmail.setText("");

            }
        }
    }

    public void resetCusAction(ActionEvent actionEvent) {
        txtName.setText("");
        txtNumber.setText("");
        txtEmail.setText("");
        searchText.setText("");
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtNumber.setEditable(false);
    }

    public void printBill(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/Reports/Invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            Connection connection = ((SessionImpl) HibernateUtil.getSession()).connection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
