package pl.lasota.tool.sr.security;

import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString(callSuper = true)
public class SpecialPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private String privileged;

    @Column(nullable = false)
    private short permission;


    public SpecialPermission() {
    }

    public SpecialPermission(String privileged, short permission) {
        this.privileged = privileged;
        this.permission = permission;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrivileged() {
        return privileged;
    }

    public void setPrivileged(String privileged) {
        this.privileged = privileged;
    }

    public short getPermission() {
        return permission;
    }

    public void setPermission(short permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialPermission that = (SpecialPermission) o;
        return permission == that.permission &&
                Objects.equals(privileged, that.privileged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privileged, permission);
    }
}
