package pl.lasota.tool.orm.security;

import pl.lasota.tool.orm.common.Access;

import java.util.Set;


public interface ObjectSecurity {

    public Set<Access> getAccesses();

    public void setAccesses(Set<Access> accesses);
}
