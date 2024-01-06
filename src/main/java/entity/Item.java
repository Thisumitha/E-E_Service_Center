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
    private int qtyOnHand;
    private double unitPrice;

    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetails = new ArrayList<>();
    public Item(String code, String name, int qtyOnHand, double unitPrice) {
        this.code = code;
        this.name = name;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }
}
