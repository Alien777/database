package pl.lasota.tool.crud.serach;

import lombok.ToString;

@ToString(callSuper=true)
public class FieldString extends Field<String> {
    private final ConditionString condition;

    public FieldString(String name, String value, PredictionType predictionType, ConditionString condition) {
        super(name, value, predictionType);
        this.condition = condition;
    }

    public ConditionString getCondition() {
        return condition;
    }
}
