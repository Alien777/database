package pl.lasota.tool.orm.field;

import java.util.Arrays;
import java.util.Optional;

public enum Operator {
    LT, LE, EQUALS, GT, GE, BETWEEN, LIKE_L, LIKE_P, LIKE, SORT, SET, SIMPLE, KEYWORD, PHRASE;

    public static Operator find(String[] pre) {
        Optional<Operator> first = Arrays.stream(Operator.values())
                .map(Operator::toString)
                .filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s)))
                .map(Operator::valueOf)
                .findFirst();
        return first.orElse(Operator.EQUALS);
    }
}
