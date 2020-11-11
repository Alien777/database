package pl.lasota.database.helper;

import lombok.Data;
import lombok.ToString;
import pl.lasota.database.mapping.NotUpdating;
import pl.lasota.database.security.ProtectedEntity;

import javax.persistence.*;

@Entity
@Data
@ToString(callSuper = true)
public class Entit extends ProtectedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    @NotUpdating
    private Long id;

    @Column
    private String color;
}
