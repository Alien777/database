package pl.lasota.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lasota.tool.crud.common.AliasColumn;
import pl.lasota.tool.crud.common.EntityBase;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "\"User\"")
@Data
@Entity

public class User extends EntityBase {

    @Column
    public String email;

    @Column
    public String password;

    @Column
    @AliasColumn(names = "login")
    public String name;

    @Column
    public Integer a;


    @Column
    public Integer b;


    @Column()
    public Double c;


    @Column
    public Double d;

    @Column(name = "moja_bardzo_wazna_zmienna_dwa")
    public Double mojaBardzoWaznaZmiennaDwa;

}
