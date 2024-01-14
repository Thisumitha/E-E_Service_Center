package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RepairItemTm extends RecursiveTreeObject<RepairItemTm> {
    private String itemCode;
    private String itemName;
    private JFXButton btn;
    private  String Customer;
    private String status;
}
