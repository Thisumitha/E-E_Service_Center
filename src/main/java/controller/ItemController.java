package controller;

import com.jfoenix.controls.JFXButton;
import dto.catelog.ItemCatolog;
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
    private  ItemCatolog itemCatolog;

    public void addToCart(ActionEvent actionEvent) {
        System.out.println(this.itemCatolog);
    }
    public  void setData (ItemCatolog item ){
        this.itemCatolog=item;
        this.nameLabel.setText(item.getName());
        this.codeLabel.setText(item.getCode());
        this.priceLabel.setText("RS :"+item.getPrice());
        Image image = new Image(item.getImgsrc());
        this.img.setImage(image);

    }



}
