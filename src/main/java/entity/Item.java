package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    private String image;
    private Boolean isDisabled;

    @ManyToOne
    @JoinColumn(name = "type")
     Type type;
    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetailsList = new ArrayList<>();

    public Item(String code, String name, double unitPrice, int qtyOnHand, String image, Boolean isDisabled) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.image = image;
        this.isDisabled = isDisabled;
    }
}
