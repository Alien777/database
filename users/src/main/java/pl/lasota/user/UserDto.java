package pl.lasota.user;


import lombok.Data;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.UpdatingSecurity;

import java.util.List;
import java.util.Set;

@Data
public class UserDto implements UpdatingSecurity {


    public String name;
    public Long id;
    public Address address;
    public Set<Access> accesses;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Set<Access> getAccesses() {
        return accesses;
    }


    @Override
    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }
}
