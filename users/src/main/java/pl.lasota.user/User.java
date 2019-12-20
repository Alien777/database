package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lasota.tool.crud.repository.EntityBase;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"User\"")
@Data
@Entity
public class User extends EntityBase<Long> {

    @Column
    public String email;

    @Column
    public String password;

    @Column
    public String name;

}
