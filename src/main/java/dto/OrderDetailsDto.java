package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailsDto {
    private String orderId;
    private String itemId;
    private int qty;
    private double price;
}