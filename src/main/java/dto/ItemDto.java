package dto;

import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemDto {
    private String code;
    private String name;
    private double unitPrice;
    private int qty;
    private String type;
    private String image;


}
