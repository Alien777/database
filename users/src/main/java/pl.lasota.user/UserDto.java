package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lasota.tool.crud.repository.AliasName;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class UserDto {
    public long id;

    public String name;
    public String password;
}
