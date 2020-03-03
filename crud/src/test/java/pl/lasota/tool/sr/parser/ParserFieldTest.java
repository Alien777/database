package pl.lasota.tool.sr.parser;


import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.lasota.tool.sr.field.*;
import pl.lasota.tool.sr.field.definition.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserFieldTest {

    private final ParserField parserField = new ParserField();

    @Test
    public void parser() throws Exception {
        List<Field<?>> parse = parserField.parse(createMVP("?adam=12"));
        assertThat(parse)
                .hasSize(1)
                .anyMatch(field -> field.getValue().equals("12"))
                .anyMatch(field -> field instanceof SimpleField);

        parse = parserField.parse(createMVP("?adam=12&pole=adamek lasota"));
        assertThat(parse)
                .hasSize(2)
                .anyMatch(field -> field.getValue().equals("12"))
                .anyMatch(field -> field.getValue().equals("adamek lasota"))
                .anyMatch(field -> field instanceof SimpleField);

        parse = parserField.parse(createMVP("?adam=12&pole=adamek lasota"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(SimpleField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue()).isEqualTo("adamek lasota");
                    assertThat(s.getSelector()).isEqualTo(Selector.AND);
                    assertThat(s.condition()).isEqualTo(Operator.EQUALS);
                });



        parse = parserField.parse(createMVP("?adam=12&pole=DESC;SORT"));
        parse.forEach(field -> {

            System.out.println(field);
        });
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(SortField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue()).isEqualTo(Sort.DESC);
                    assertThat(s.getSelector()).isEqualTo(Selector.SORT);
                    assertThat(s.condition()).isEqualTo(Operator.SORT);
                });

        parse = parserField.parse(createMVP("?adam=12&pole=adamek;OR;GE"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(SimpleField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue()).isEqualTo("adamek");
                    assertThat(s.getSelector()).isEqualTo(Selector.OR);
                    assertThat(s.condition()).isEqualTo(Operator.GE);
                });

        parse = parserField.parse(createMVP("?adam=12&pole=adamek;OR"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(SimpleField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue()).isEqualTo("adamek");
                    assertThat(s.getSelector()).isEqualTo(Selector.OR);
                    assertThat(s.condition()).isEqualTo(Operator.EQUALS);
                });

        parse = parserField.parse(createMVP("?adam=12&pole=12!23;OR"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(RangeField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue().getMinimum()).isEqualTo("12");
                    assertThat(s.getValue().getMaximum()).isEqualTo("23");
                    assertThat(s.getSelector()).isEqualTo(Selector.OR);
                    assertThat(s.condition()).isEqualTo(Operator.RANGE);
                });

        parse = parserField.parse(createMVP("?adam=12&pole=12!23;and;"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(RangeField.class, s -> {
                    assertThat(s.getName()).isEqualTo("pole");
                    assertThat(s.getValue().getMinimum()).isEqualTo("12");
                    assertThat(s.getValue().getMaximum()).isEqualTo("23");
                    assertThat(s.getSelector()).isEqualTo(Selector.AND);
                    assertThat(s.condition()).isEqualTo(Operator.RANGE);
                });

        parse = parserField.parse(createMVP("?adam=12&page=12!23"));
        assertThat(parse)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(PageField.class, s -> {
                    assertThat(s.getValue().getLimit()).isEqualTo(23);
                    assertThat(s.getValue().getPage()).isEqualTo(12);
                });
    }

    private MultiValueMap<String, String> createMVP(String url) {
        String[] field = url.split("&");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (String s : field) {
            String[] split = s.split("=");
            if (split.length == 1) {
                map.add(split[0], "");
            } else {
                map.add(split[0], split[1]);
            }
        }
        return map;
    }
}
