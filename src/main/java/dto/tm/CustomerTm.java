package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerTm extends RecursiveTreeObject<CustomerTm> {
    private String code;
    private String name;
    private int number;
    private String Email;



}
