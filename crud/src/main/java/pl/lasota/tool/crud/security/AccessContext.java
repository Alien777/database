package pl.lasota.tool.crud.security;


import java.util.Objects;

public class AccessContext {

    private String name;
    private short rud;

    public AccessContext(String name, short rud) {
        this.name = name;
        this.rud = rud;
    }

    public AccessContext(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getRud() {
        return rud;
    }

    public void setRud(short rud) {
        this.rud = rud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessContext that = (AccessContext) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
