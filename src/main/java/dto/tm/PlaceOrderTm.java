package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PlaceOrderTm extends RecursiveTreeObject<PlaceOrderTm> {
    private String code;
    private String name;
    private double unitPrice;
    private JFXButton btnMinus;
    private int qty;
    private JFXButton btnPlus;
    private double price;
    private JFXButton option;
}
