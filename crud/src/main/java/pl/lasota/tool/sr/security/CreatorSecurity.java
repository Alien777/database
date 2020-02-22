package pl.lasota.tool.sr.security;

import java.util.Set;

public class CreatorSecurity implements CreatableSecurity {

    private transient Set<Access> accesses;

    @Override
    public Set<Access> getAccesses() {
        return accesses;
    }

    @Override
    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }

}
