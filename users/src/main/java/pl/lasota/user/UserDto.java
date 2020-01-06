package pl.lasota.user;


import lombok.Data;
import pl.lasota.tool.crud.common.Access;
import pl.lasota.tool.crud.mapping.CopyByReference;
import pl.lasota.tool.crud.security.UpdatingSecurity;

import java.util.List;
import java.util.Set;

@Data
public class UserDto implements UpdatingSecurity {


    public String name;
    public Long id;
    public Set<Access> accesses;


    public List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
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
    public Set<Access> getAccesses() {
        return accesses;
    }


    @Override
    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }
}
