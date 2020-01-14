package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.mapping.CopyByReference;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"User\"")
@Data
@Entity
@ToString(callSuper = true)
public class User extends EntitySecurity {

    @Column
    public String name;

    @OneToOne(cascade = CascadeType.ALL)
    public Address address;

    @ElementCollection
    @CopyByReference
    public List<String> list;
}
