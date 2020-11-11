package pl.lasota.database.it;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.database.mapping.NotUpdating;
import pl.lasota.database.repository.BasicEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Shop implements BasicEntity {

    public Shop(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    @NotUpdating
    private Long id;

    @Column
    private String name;

    @Column
    private String secondName;

    @Column
    @NotUpdating
    private Integer value;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

}
