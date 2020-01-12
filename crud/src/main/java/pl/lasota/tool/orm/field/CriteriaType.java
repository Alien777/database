package pl.lasota.tool.orm.field;

import java.util.Arrays;
import java.util.Optional;

public enum CriteriaType  {
    AND, OR, SORT, SET, MUST, SHOULD, NOT_MUST;

    public static CriteriaType find(String[] pre) {
        Optional<CriteriaType> first = Arrays.stream(CriteriaType.values())
                .map(CriteriaType::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s)))
                .map(CriteriaType::valueOf)
                .findFirst();
        return first.orElse(CriteriaType.AND);
    }
}