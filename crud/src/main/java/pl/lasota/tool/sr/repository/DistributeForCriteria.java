package pl.lasota.tool.sr.repository;

import pl.lasota.tool.sr.field.definition.CriteriaField;
import pl.lasota.tool.sr.field.definition.SetField;
import pl.lasota.tool.sr.field.definition.SortField;

import java.util.List;

public interface DistributeForCriteria {

    List<SetField> set();

    List<CriteriaField<?>> and();

    List<CriteriaField<?>> or();

    SortField sort();
}
