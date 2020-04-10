package pl.lasota.tool.sr.it;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Product implements BasicEntity {

    public Product(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    @NotUpdating
    private Long id;

    @Column
    private String name;
}
