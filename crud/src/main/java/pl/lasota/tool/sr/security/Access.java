package pl.lasota.tool.sr.security;

import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@ToString(callSuper = true)
public class Access {

    public static final String SEPARATOR = "___";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private short rud;

    @Column(nullable = false)
    private String value;

    public Access() {
    }

    public Access(String name, short rud) {
        this.rud = rud;
        this.name = name;
        value = name + SEPARATOR + rud;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
