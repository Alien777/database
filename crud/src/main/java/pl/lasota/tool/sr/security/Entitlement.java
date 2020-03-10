package pl.lasota.tool.sr.security;

import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.BasicEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@ToString(callSuper = true)
public class Entitlement extends BasicEntity implements Entitling {


    @Column(nullable = false)
    @NotUpdating
    private String owner;

    @Column(name = "\"group\"")
    @NotUpdating
    private String group;

    @Column(nullable = false)
    @NotUpdating
    private Short permission;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @NotUpdating
    @JoinTable(name = "entitlement_special_permission",
            joinColumns = @JoinColumn(name = "entitlement_id"),
            inverseJoinColumns = @JoinColumn(name = "special_permission_id", unique = true)
    )
    private Set<SpecialPermission> specialPermissions;

    public void copySet(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions=specialPermissions.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }

    public Set<SpecialPermission> copyGet() {
        return this.specialPermissions.stream()
                .map(access -> new SpecialPermission(access.getPrivileged(), access.getPermission())).collect(Collectors.toSet());
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
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
    public void setSpecialPermissions(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions = specialPermissions;
    }
}
