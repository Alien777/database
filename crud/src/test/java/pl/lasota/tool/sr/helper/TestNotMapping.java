package pl.lasota.tool.sr.helper;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.security.EntitySecurity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@ToString(callSuper = true)
public class TestNotMapping extends EntitySecurity {

    @Column
    @NotUpdating
    private String color;
}
