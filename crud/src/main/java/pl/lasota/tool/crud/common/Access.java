package pl.lasota.tool.crud.common;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",unique=true, nullable = false)
    private long id;

    @Column
    private String name;

    @Column
    private short rud;

    public Access() {
    }

    public Access(String name, short rud) {
        this.rud = rud;
        this.name = name;
    }

    public short getRud() {
        return rud;
    }

    public void setRud(short rud) {
        this.rud = rud;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Access access = (Access) o;
        return Objects.equals(name, access.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
