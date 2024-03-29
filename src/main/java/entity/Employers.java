package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Employers {
    @Id
    private String code;
    private String name;
    private Integer number;
    private String email;
    private String password;

    @OneToOne(mappedBy = "employer", cascade = CascadeType.ALL)
    private Access access;

    public Employers(String code, String name, Integer number, String email, String password) {
        this.code = code;
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
    }
}
