package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId")
    Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    Orders orders;

    private int qty;
    private double price;


}