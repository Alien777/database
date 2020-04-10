package pl.lasota.tool.sr.helper;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.security.ProtectedEntity;

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
