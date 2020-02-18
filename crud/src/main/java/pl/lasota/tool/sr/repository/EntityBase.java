package pl.lasota.tool.sr.repository;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;

import javax.persistence.*;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    @NotUpdating
    private Long id;

    public EntityBase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
