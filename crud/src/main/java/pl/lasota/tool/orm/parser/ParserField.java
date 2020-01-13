package pl.lasota.tool.orm.parser;

import org.springframework.util.MultiValueMap;
import pl.lasota.tool.orm.field.Operator;
import pl.lasota.tool.orm.field.Selector;
import pl.lasota.tool.orm.common.Sort;
import pl.lasota.tool.orm.field.*;

import pl.lasota.tool.orm.repository.field.*;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ParserField implements Parser<MultiValueMap<String, String>, List<Field<?>>> {


    private static final String BETWEEN_SEPARATOR = "!";
    private static final String PAGE = "page";

    //?username=adam,or,like&text,int,or
    @Override
    public List<Field<?>> parse(MultiValueMap<String, String> formula) throws Exception {
        List<Field<?>> fields = new LinkedList<>();
        try {
            formula.forEach((nameField, value) -> {
                value.stream().map(s -> s.split(";")).forEach(secondRaw -> {
                    String valueField = secondRaw[0];
                    String[] withoutValue = Arrays.copyOfRange(secondRaw, 1, secondRaw.length);
                    Selector selectorField = Selector.find(withoutValue);


                    if (selectorField == Selector.SET) {
                        fields.add(new SetField(nameField,valueField));
                    }else
                    if (nameField.equals(PAGE)) {
                        String[] pages = valueField.split(BETWEEN_SEPARATOR);
                        fields.add(new PaginationField(new Pageable( Integer.parseInt(pages[0]),Integer.parseInt(pages[1]))));
                    } else if (isBetweenNumber(valueField)) {
                        try {
                            String[] values = valueField.split(BETWEEN_SEPARATOR);
                            Range<String> numberRange = new Range<>(values[0].trim(), values[1].trim());
                            fields.add(new RangeStringField(nameField, numberRange, selectorField));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        Operator operatorField = Operator.find(withoutValue);
                        if (Operator.SORT == operatorField) {
                            fields.add(new SortField(nameField, Sort.find(secondRaw)));
                        } else {
                            try {
                                fields.add(new StringField(nameField, valueField, selectorField, operatorField));
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });
            });
        } catch (RuntimeException e) {
            throw new Exception(e);
        }

        return fields;
    }

    private boolean isBetweenNumber(String valueField) {
        String[] split = valueField.split(BETWEEN_SEPARATOR);
        return split.length == 2;
    }
}
