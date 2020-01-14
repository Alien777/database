package pl.lasota.user;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.mapping.CopyByReference;

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
