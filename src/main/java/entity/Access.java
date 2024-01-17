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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private boolean storeAccess;
    private boolean inventoryAccess;
    private boolean customerAccess;
    private boolean reportAccess;
    private boolean repairAccess;

    @OneToOne
    @JoinColumn(name = "employer")
    private Employers employer;

    public Access(boolean storeAccess, boolean inventoryAccess, boolean customerAccess, boolean reportAccess, boolean repairAccess) {
        this.storeAccess = storeAccess;
        this.inventoryAccess = inventoryAccess;
        this.customerAccess = customerAccess;
        this.reportAccess = reportAccess;
        this.repairAccess = repairAccess;
    }
}
