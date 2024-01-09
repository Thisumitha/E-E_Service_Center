package dto.catelog;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemCatolog {
    private String name;
    private String code;
    private String imgsrc;
    private double price;
    private JFXButton btn;
}
