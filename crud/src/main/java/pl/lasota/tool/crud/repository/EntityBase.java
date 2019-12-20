package pl.lasota.tool.crud.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class EntityBase<ID extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",unique=true, nullable = false)
    private ID id;
}
