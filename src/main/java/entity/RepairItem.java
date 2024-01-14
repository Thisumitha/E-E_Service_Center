package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class RepairItem {
    @Id
    private String id;
    private String name;
    private String date;
    private String cashier;
    private String price;
    private String note;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "item")
    private List<RepairaPartsDetails> repairParts = new ArrayList<>();


    public RepairItem(String id, String name, String date, String cashier, String price, String note) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.cashier = cashier;
        this.price = price;
        this.note = note;
    }
}
