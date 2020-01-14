package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.sr.security.EntitySecurity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"user\"")
@Data
@Entity
@ToString(callSuper = true)
public class User extends EntitySecurity {

    @Column
    public String name;

    @OneToOne(cascade = CascadeType.ALL)
    public Address address;

}
