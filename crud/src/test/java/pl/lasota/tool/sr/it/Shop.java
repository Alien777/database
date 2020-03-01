package pl.lasota.tool.sr.it;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.tool.sr.security.EntitySecurity;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Shop extends EntitySecurity {

    private String value;
}
