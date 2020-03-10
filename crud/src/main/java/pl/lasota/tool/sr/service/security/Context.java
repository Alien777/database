package pl.lasota.tool.sr.service.security;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
public final class Context {
    public final String owner;
    public final String group;
    public final List<String> privilege;


    public Context(String owner) {
        this(owner, null, new LinkedList<>());
    }

    public Context(String owner, List<String> privilege) {
        this(owner, null, privilege);
    }

    public Context(String owner, String group) {
        this.owner = owner;
        this.group = group;
        this.privilege = null;
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

    public Context(String owner,  String... privilege) {
        this.owner = owner;
        this.group = null;
        this.privilege = Arrays.asList(privilege);
    }
}
