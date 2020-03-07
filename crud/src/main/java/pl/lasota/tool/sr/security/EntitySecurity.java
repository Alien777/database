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
    @NotUpdating
    private String owner;

    @Column(nullable = false, name = "\"group\"")
    @NotUpdating
    private String group;

    @Column(nullable = false)
    @NotUpdating
    private Short permission;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @NotUpdating
    @JoinTable(name = "entity_authorization",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_id", unique = true)
    )
    private Set<SpecialPermission> specialPermissions;


    public void copySet(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions = specialPermissions.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }

    public Set<SpecialPermission> copyGet() {
        return this.specialPermissions.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String user) {
        this.owner = user;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public Short getPermission() {
        return permission;
    }

    @Override
    public void setPermission(Short permission) {
        this.permission = permission;
    }

    @Override
    public Set<SpecialPermission> getSpecialPermissions() {
        return specialPermissions;
    }

    @Override
    public void setSpecialPermissions(Set<SpecialPermission> specialPermission) {
        this.specialPermissions = specialPermission;
    }
}
