package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Type {
    @Id

    private String type;
    private String category;
    private int id;

    @OneToMany(mappedBy = "type")
    private List<Item> items = new ArrayList<>();

    public Type(String type, String category, int id) {
        this.type = type;
        this.category = category;
        this.id = id;
    }
}
