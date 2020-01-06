package pl.lasota.tool.crud.security;

import java.util.HashSet;
import java.util.Set;

public class Context {

    private Set<AccessContext> secured = new HashSet<>();

    public Set<AccessContext> getSecured() {
        return secured;
    }

    public void setSecured(Set<AccessContext> secured) {
        this.secured = secured;
    }

    public void add(AccessContext context) {
        secured.add(context);
    }
}
