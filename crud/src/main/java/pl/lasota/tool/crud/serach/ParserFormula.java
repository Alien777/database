package pl.lasota.tool.crud.serach;

import java.text.NumberFormat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ParserFormula implements Parser<String, List<Field>> {

    private final static Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");


    //?username=adam,or,like&text,int,or
    @Override
    public List<Field> parse(String formula) throws Exception{
        List<Field> fields = new LinkedList<>();
        int i = formula.indexOf("?");
        String[] rawFields = formula.substring(i + 1).split("&");
        for (String rawField : rawFields) {
            String[] firstRaw = rawField.split("=");
            String nameField = firstRaw[0];

            String[] secondRaw = firstRaw[1].split(";");
            String valueField = secondRaw[0];
            String[] withoutValue = Arrays.copyOfRange(secondRaw, 1, secondRaw.length);
            PredictionType predictionTypeField = findType(withoutValue);

            if (isNumeric(valueField)) {
                ConditionNumber conditionNumberField = findConditionTypeInteger(withoutValue);
                fields.add(new FieldNumber(nameField, NumberFormat.getInstance()
                        .parse(valueField.replace(".", ",").trim()), predictionTypeField, conditionNumberField));
            }
            if (!isNumeric(valueField)) {
                ConditionString conditionStringField = findConditionTypeString(withoutValue);
                fields.add(new FieldString(nameField, valueField, predictionTypeField, conditionStringField));
            }
        }

        return fields;
    }


    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return NUMBER_PATTERN.matcher(strNum).matches();
    }


    private PredictionType findType(String[] pre) {

        Optional<PredictionType> first = Arrays.stream(PredictionType.values())
                .map(PredictionType::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s))).map(PredictionType::valueOf)
                .findFirst();
        return first.orElse(PredictionType.AND);
    }

    private ConditionString findConditionTypeString(String[] pre) {
        Optional<ConditionString> first = Arrays.stream(ConditionString.values())
                .map(ConditionString::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s))).map(ConditionString::valueOf)
                .findFirst();
        return first.orElse(ConditionString.EQUALS);
    }

    private ConditionNumber findConditionTypeInteger(String[] pre) {
        Optional<ConditionNumber> first = Arrays.stream(ConditionNumber.values())
                .map(ConditionNumber::toString).filter(s -> Arrays.stream(pre).anyMatch(s1 -> s1.toUpperCase().equals(s))).map(ConditionNumber::valueOf)
                .findFirst();
        return first.orElse(ConditionNumber.EQUALS);
    }


}
