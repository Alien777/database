package pl.lasota.tool.crud.serach;

import lombok.ToString;

@ToString(callSuper=true)
public class FieldNumber extends Field<Number>{
    private final ConditionNumber condition;

    FieldNumber(String name, Number value, PredictionType predictionType, ConditionNumber condition) {
        super(name, value, predictionType);
        this.condition = condition;
    }

    public ConditionNumber getCondition() {
        return condition;
    }
}
