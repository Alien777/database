package pl.lasota.tool.sr.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lasota.tool.sr.mapping.NotUpdating;
import pl.lasota.tool.sr.security.CreatableSecurity;
import pl.lasota.tool.sr.security.EntitySecurity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectTest {

    @NotUpdating
    private String colorSecond;
}
