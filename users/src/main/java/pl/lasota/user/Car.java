package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.security.UpdatingSecurity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
public class Car extends EntitySecurity implements UpdatingSecurity {

    @Column
    public String color;


}
