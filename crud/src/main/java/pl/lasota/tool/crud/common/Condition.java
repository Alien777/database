package pl.lasota.tool.crud.common;

import java.util.Arrays;
import java.util.Optional;

public enum Condition {
    LT, LE, EQUALS, GT, GE, BETWEEN, LIKE_L, LIKE_P, LIKE, SORT, SET;

    public static Condition find(String[] pre) {
        Optional<Condition> first = Arrays.stream(Condition.values())
                .map(Condition::toString)
                .filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s)))
                .map(Condition::valueOf)
                .findFirst();
        return first.orElse(Condition.EQUALS);
    }
}
