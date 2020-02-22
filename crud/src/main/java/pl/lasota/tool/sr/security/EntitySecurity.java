package pl.lasota.tool.sr.security;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.EntityBase;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(callSuper = true)
public abstract class EntitySecurity extends EntityBase {

    public EntitySecurity() {
    }

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @NotUpdating
    @JoinTable(name = "entity_access",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id", unique = true)
    )
    private Set<Access> accesses;

    public Set<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }

    public void copySet(Set<Access> accesses) {
        this.accesses = accesses.stream()
                .map(access -> new Access(access.getName(), access.getRud())).collect(Collectors.toSet());
    }

    public Set<Access> copyGet() {
        return this.accesses.stream()
                .map(access -> new Access(access.getName(), access.getRud())).collect(Collectors.toSet());
    }
}
