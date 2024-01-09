package entity;

import lombok.*;
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
    private String id;


    private String type;
    private String category;


    @OneToMany(mappedBy = "type")
    private List<Item> items = new ArrayList<>();

    public Type(String type, String category, String id) {
        this.type = type;
        this.category = category;
        this.id = id;
    }
}
