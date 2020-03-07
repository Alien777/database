package pl.lasota.tool.sr.it;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.security.EntitySecurity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop extends EntityBase {

    @Column
    private String name;

    @Column
    @NotUpdating
    private Integer value;
}
