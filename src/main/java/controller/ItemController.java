package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import dao.util.BoType;
import dto.catelog.ItemCatologDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemController {
    public ImageView img;
    public Label nameLabel;

    public Label priceLabel;
    public Label codeLabel;
    public JFXButton btn;
    private ItemCatologDto itemCatologDto;
    StoreFormController storeFormController =new StoreFormController();


    public void addToCart(ActionEvent actionEvent) {
        System.out.println(this.itemCatologDto);
        storeFormController.savecart(this.itemCatologDto );

    }
    public  void setData (ItemCatologDto item ){
        this.itemCatologDto =item;
        this.nameLabel.setText(item.getName());
        this.codeLabel.setText(item.getCode());
        this.priceLabel.setText("RS :"+item.getPrice());
        Image image = new Image(item.getImgsrc());
        this.img.setImage(image);

    }



}
