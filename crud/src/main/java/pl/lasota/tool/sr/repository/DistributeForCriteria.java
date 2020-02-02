package pl.lasota.tool.sr.repository;

import pl.lasota.tool.sr.field.CriteriaField;
import pl.lasota.tool.sr.field.SetField;
import pl.lasota.tool.sr.field.SortField;

import java.util.List;

public interface DistributeForCriteria {

    List<SetField> set();

    List<CriteriaField<?>> and();

    List<CriteriaField<?>> or();

    SortField sort();
}
