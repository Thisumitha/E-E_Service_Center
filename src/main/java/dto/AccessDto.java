package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccessDto {
    private boolean storeAccess;
    private boolean inventoryAccess;
    private boolean customerAccess;
    private boolean reportAccess;
    private boolean repairAccess;
}
