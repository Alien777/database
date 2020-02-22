package pl.lasota.tool.sr.helper;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.security.CreatableSecurity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@ToString(callSuper = true)
public class Entit extends EntitySecurity implements CreatableSecurity {

    @Column
    private String color;
}
