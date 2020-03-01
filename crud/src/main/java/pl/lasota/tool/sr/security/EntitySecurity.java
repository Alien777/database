package pl.lasota.tool.sr.security;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.EntityBase;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(callSuper = true)
public abstract class EntitySecurity extends EntityBase implements CreatableSecurity {

    public static final String AUTHORIZATION_PRIVILEGED = "authorization.privileged";

    public static final String AUTHORIZATION_PERMISSION = "authorization.permission";

    public EntitySecurity() {
    }

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private String group;

    @Column(nullable = false)
    private short permission;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @NotUpdating
    @JoinTable(name = "entity_authorization",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_id", unique = true)
    )
    private Set<SpecialPermission> specialPermission;

    public Set<SpecialPermission> getSpecialPermission() {
        return specialPermission;
    }

    public void setSpecialPermission(Set<SpecialPermission> specialPermissions) {
        this.specialPermission = specialPermissions;
    }

    public void copySet(Set<SpecialPermission> specialPermissions) {
        this.specialPermission = specialPermissions.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }

    public Set<SpecialPermission> copyGet() {
        return this.specialPermission.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String user) {
        this.owner = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String group) {
        this.role = group;
    }

    @Override
    public short getPermission() {
        return permission;
    }

    @Override
    public void setPermission(short permission) {
        this.permission = permission;
    }

    private class Shrot {
    }
}
