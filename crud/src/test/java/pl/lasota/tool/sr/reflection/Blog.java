package pl.lasota.tool.sr.reflection;

import lombok.Data;
import pl.lasota.tool.sr.mapping.CopyByReference;
import pl.lasota.tool.sr.repository.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class Blog extends EntityBase {

    @Column(unique = true, length = 100)
    private String url;

    @Column(length = 100)
    @CopyByReference
    private String name;

    @Column(length = 150)
    private String metaTitle;

    @Column(length = 300)
    private String metaDescription;

    @Column(length = 150000)
    private String body;

    @Column
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;

}
