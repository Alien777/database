package pl.lasota.tool.sr.security;

import java.util.Set;


public interface CreatableSecurity {

    Set<Access> getAccesses();

    void setAccesses(Set<Access> accesses);
}
