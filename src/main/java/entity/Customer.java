package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
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
    private String Email;

}
