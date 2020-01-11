package pl.lasota.tool.orm.common;

import pl.lasota.tool.orm.repository.field.CriteriaField;

import java.util.List;

public interface Processable {

    void process(List<CriteriaField<?>> fields);

}


