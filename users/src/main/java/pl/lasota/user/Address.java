package pl.lasota.user;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.mapping.CopyByReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@ToString
@Data
@Table
public class Address extends EntityBase {

    @Column
    @CopyByReference
    private String street;


}
