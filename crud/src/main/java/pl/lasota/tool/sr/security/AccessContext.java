package pl.lasota.tool.sr.security;

import lombok.ToString;

@ToString(callSuper = true)
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


}
