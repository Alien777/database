package pl.lasota.user;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.mapping.CopyByReference;

import javax.persistence.Entity;


@Entity
@ToString
@Data
public class Address extends EntityBase {

    @CopyByReference
    private String street;

}
