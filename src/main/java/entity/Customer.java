package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String address;
    private String number;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders = new ArrayList<>();

    public Customer(String id, String name, String address, String number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
    }
}