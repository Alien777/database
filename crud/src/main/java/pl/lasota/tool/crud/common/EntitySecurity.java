package pl.lasota.tool.crud.common;


import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.Set;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class EntitySecurity extends EntityBase {

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Access> accesses;

    public EntitySecurity() {
    }

    public Set<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }
}
