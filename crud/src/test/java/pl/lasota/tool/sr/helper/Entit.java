package pl.lasota.tool.sr.helper;

import lombok.Data;
import lombok.ToString;
import pl.lasota.tool.sr.security.ProtectedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@ToString(callSuper = true)
public class Entit extends ProtectedEntity {

    @Column
    private String color;
}
