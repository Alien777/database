package pl.lasota.tool.crud.common;

import java.util.Arrays;
import java.util.Optional;

public enum Sort {
    ASC, DESC;

    public static Sort find(String[] pre) {
        Optional<Sort> first = Arrays.stream(Sort.values())
                .map(Sort::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s)))
                .map(Sort::valueOf)
                .findFirst();
        return first.orElse(Sort.ASC);
    }
}
