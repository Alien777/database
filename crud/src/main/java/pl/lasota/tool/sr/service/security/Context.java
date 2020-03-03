package pl.lasota.tool.sr.service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
public final class Context {
    public final String username;
    public final String role;
    public final List<String> privilege;

    public Context(String username, String role) {
        this(username, role, null);
    }

    public Context(String username, String role, List<String> privilege) {
        this.username = username;
        this.role = role;
        this.privilege = privilege;
    }
}
