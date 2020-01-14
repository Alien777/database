package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.sr.mapping.CopyByReference;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.security.UpdatingSecurity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"Car\"")
@Data
@Entity
@ToString(callSuper = true)
public class Car extends EntitySecurity implements UpdatingSecurity {

    @Column
    public String color;

}
