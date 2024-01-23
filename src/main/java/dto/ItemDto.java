package dto;

import lombok.*;


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
    private Boolean isDisabled;

}
