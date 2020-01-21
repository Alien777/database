package pl.lasota.tool.sr.security;

import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString(callSuper = true)
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
