package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RepairPartDto {
    private String id;
    private String name;
    private int qty;
    private double price;
    private String itemcode;
}
