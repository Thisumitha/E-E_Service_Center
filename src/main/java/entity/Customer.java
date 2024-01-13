package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Customer {
    @Id
    private String code;
    private String name;
    private int number;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<RepairItem> repairItems = new ArrayList<>();

    public Customer(String code, String name, int number, String email) {
        this.code = code;
        this.name = name;
        this.number = number;
        this.email = email;
    }
}
