package dto.tm;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CatologTm extends RecursiveTreeObject<CatologTm> {
    private  String id;
    private String name;
    private JFXButton btnMinus;
    private int qty;
    private JFXButton btnPlus;
    private double price;
}
