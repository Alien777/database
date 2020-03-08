package pl.lasota.tool.sr.security;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(callSuper = true)
public abstract class ProtectedEntity extends BasicEntity implements SecurityProvider {

    public ProtectedEntity() {
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "entitlement_id", referencedColumnName = "id", updatable = false)
    @NotUpdating
    private Entitlement entitlement = new Entitlement();


    public Entitlement getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }
}
