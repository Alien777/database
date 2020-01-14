package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.sr.mapping.CopyByReference;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.security.UpdatingSecurity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
public class Car extends EntitySecurity implements UpdatingSecurity {

    @Column
    public String color;

}
