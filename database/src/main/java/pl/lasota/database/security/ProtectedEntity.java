package pl.lasota.database.security;

import lombok.ToString;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.mapping.NotUpdating;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(callSuper = true)
public abstract class ProtectedEntity implements BasicEntity, SecurityProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_sequence")
    @SequenceGenerator(name = "option_sequence", sequenceName = "protected_entity_id_seq", allocationSize = 1)
    @Column(unique = true, nullable = false)
    @NotUpdating
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "entitlement_id", referencedColumnName = "id", updatable = false)
    @NotUpdating
    private Entitlement entitlement = new Entitlement();

    public ProtectedEntity() {
    }

    public Entitlement getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }
}
