package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class EmployerTm extends RecursiveTreeObject<EmployerTm> {
    private String code;
    private String name;
    private String email;
    private JFXButton btn;
}
