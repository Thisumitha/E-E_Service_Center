package dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RepairPartsTm {
    private String id;
    private String name;
    private int qty;
    private double price;
}
