package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemTm extends RecursiveTreeObject<ItemTm> {

    private String code;
    private String name;
    private double unitPrice;
    private int qty;
    private String category;
    private String type;
    private JFXButton btn;


}
