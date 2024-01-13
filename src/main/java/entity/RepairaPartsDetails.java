package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
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




}