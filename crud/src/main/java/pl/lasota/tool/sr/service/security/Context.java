package pl.lasota.tool.sr.service.security;

import lombok.Getter;

import java.util.*;

@Getter
public final class Context {
    public final String owner;
    public final String group;
    public final List<String> privilege;

    public Context(String owner, String group) {
        this(owner, group, new LinkedList<>());
    }

    public Context(String owner, String group, List<String> privilege) {
        this.owner = owner;
        this.group = group;
        this.privilege = privilege;
    }

    public Context(String owner, String group, String... privilege) {
        this.owner = owner;
        this.group = group;
        this.privilege = Arrays.asList(privilege);
    }
}
