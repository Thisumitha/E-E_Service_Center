package dto;

import entity.Customer;
import entity.RepairItem;
import entity.RepairaPartsDetails;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RepairItemDto {
    private String id;
    private String name;
    private String endDate;
    private String orderDate;
    private String status;
    private String cashier;
    private String price;
    private String note;
    private Customer customer;
    private List<RepairaPartsDetails> repairItems;
}
