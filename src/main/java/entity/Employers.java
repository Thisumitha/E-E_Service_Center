package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Employers {
    @Id
    private String code;
    private String name;
    private int number;
    private String email;

    @OneToOne(mappedBy = "employer")
    private Access access;

    public Employers(String code, String name, int number, String email) {
        this.code = code;
        this.name = name;
        this.number = number;
        this.email = email;
    }
}
