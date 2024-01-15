package entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class RepairaPartsDetails {
  @Id
    private String id;
    private String name;
    private int qty;
    private double price;

    @ManyToOne
    @JoinColumn(name = "repair_id")
    private RepairItem item;

  public RepairaPartsDetails(String id, String name, int qty, double price) {
    this.id = id;
    this.name = name;
    this.qty = qty;
    this.price = price;
  }
}