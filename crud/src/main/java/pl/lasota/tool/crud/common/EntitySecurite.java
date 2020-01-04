package pl.lasota.tool.crud.common;

import javax.persistence.*;

@MappedSuperclass
public abstract class EntitySecurite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",unique=true, nullable = false)
    private Long owner;


    public EntitySecurite() {
    }


}
