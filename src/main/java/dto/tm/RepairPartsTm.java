package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RepairPartsTm extends RecursiveTreeObject<RepairPartsTm> {

    private String name;
    private int qty;
    private double price;
}
