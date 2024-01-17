package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccessDto {
    private String id;
    private boolean storeAccess;
    private boolean inventoryAccess;
    private boolean customerAccess;
    private boolean reportAccess;
    private boolean repairAccess;
    private String employer;
}
