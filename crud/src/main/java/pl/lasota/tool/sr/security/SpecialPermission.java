package pl.lasota.tool.sr.security;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString(callSuper = true)
public class SpecialPermission implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_sequence")
    @SequenceGenerator(name = "option_sequence", sequenceName = "special_permission_id_seq", allocationSize = 1)
    @Column(unique = true, nullable = false)
    @NotUpdating
    private Long id;

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
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
