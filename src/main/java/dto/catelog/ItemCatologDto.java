package dto.catelog;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemCatologDto {
    private String name;
    private String code;
    private String imgsrc;
    private int qty;
    private double price;
    private JFXButton btn;
}
