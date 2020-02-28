package pl.lasota.tool.sr.security;

import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString(callSuper = true)
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private String privilege;

    @Column(nullable = false)
    private short rud;

    @Column(nullable = false)
    private String privilegeRud;

    public Access() {
    }

    public Access(String privilege, short rud, String privilegeRud) {
        this.privilege = privilege;
        this.rud = rud;
        this.privilegeRud = privilegeRud;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String name) {
        this.privilege = name;
    }

    public short getRud() {
        return rud;
    }

    public void setRud(short rud) {
        this.rud = rud;
    }

    public String getPrivilegeRud() {
        return privilegeRud;
    }

    public void setPrivilegeRud(String privilegeRud) {
        this.privilegeRud = privilegeRud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Access access = (Access) o;
        return Objects.equals(privilegeRud, access.privilegeRud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privilegeRud);
    }
}
