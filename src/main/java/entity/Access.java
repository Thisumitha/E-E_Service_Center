package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Access {
    @Id
    private String id;

    private boolean storeAccess;
    private boolean inventoryAccess;
    private boolean customerAccess;
    private boolean reportAccess;
    private boolean repairAccess;

    @OneToOne
    @JoinColumn(name = "employer_id")
    private Employers employer;

    public Access(String id, boolean storeAccess, boolean inventoryAccess, boolean customerAccess, boolean reportAccess, boolean repairAccess) {
        this.id = id;
        this.storeAccess = storeAccess;
        this.inventoryAccess = inventoryAccess;
        this.customerAccess = customerAccess;
        this.reportAccess = reportAccess;
        this.repairAccess = repairAccess;
    }


}
