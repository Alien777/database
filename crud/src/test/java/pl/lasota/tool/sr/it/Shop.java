package pl.lasota.tool.sr.it;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
    @NotUpdating
    private Integer value;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

}
