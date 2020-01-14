package pl.lasota.tool.sr.field;

import java.util.Arrays;
import java.util.Optional;

public enum Selector {
    AND, OR, SORT, SET, MUST, SHOULD, NOT_MUST;

    public static Selector find(String[] pre) {
        Optional<Selector> first = Arrays.stream(Selector.values())
                .map(Selector::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s)))
                .map(Selector::valueOf)
                .findFirst();
        return first.orElse(Selector.AND);
    }
}
