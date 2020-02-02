package pl.lasota.tool.sr.repository;

import pl.lasota.tool.sr.field.CriteriaField;
import pl.lasota.tool.sr.field.SortField;

import java.util.List;

public interface DistributeForTokenizer {

    List<CriteriaField<?>> must();

    List<CriteriaField<?>> should();

    List<CriteriaField<?>> notMust();

    SortField sort();
}
