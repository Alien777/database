package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.crud.common.AliasColumn;
import pl.lasota.tool.crud.common.EntitySecurity;
import pl.lasota.tool.crud.mapping.CopyByReference;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"User\"")
@Data
@Entity
@ToString(callSuper = true)
public class User extends EntitySecurity {

    @Column
    @AliasColumn(names = "login")
    public String name;

    @OneToOne
    public Address address;

    @ElementCollection
    @CopyByReference
    public List<String> list;
}
