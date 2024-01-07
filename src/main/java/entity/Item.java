package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Item {
    @Id
    private String code;
    private String name;
    private double unitPrice;
    private int qtyOnHand;
    private String category;
    private String type;
    private String image;


    public Item(String code, String name, double unitPrice, int qtyOnHand, String category, String type, String image) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.category = category;
        this.type = type;
        this.image = image;
    }
}
