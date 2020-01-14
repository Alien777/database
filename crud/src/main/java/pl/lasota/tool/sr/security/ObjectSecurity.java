package pl.lasota.tool.sr.security;

import java.util.Set;


public interface ObjectSecurity {

    public Set<Access> getAccesses();

    public void setAccesses(Set<Access> accesses);
}
