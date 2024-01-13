package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderDetailsTm extends RecursiveTreeObject<OrderDetailsTm> {
    private String itemId;
    private int itemQty;
    private double itemPrice;
    private String itemType;


}
