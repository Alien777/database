package pl.lasota.tool.crud.security;

import pl.lasota.tool.crud.common.Access;

import java.util.Set;


public interface ObjectSecurity {

    public Set<Access> getAccesses();

    public void setAccesses(Set<Access> accesses);
}
