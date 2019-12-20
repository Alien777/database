package pl.lasota.tool.crud.serach;

import lombok.ToString;

@ToString
public class Field<VALUE> {

    private final String name;
    private final VALUE value;
    private final PredictionType predictionType;

    Field(String name, VALUE value, PredictionType predictionType) {
        this.name = name;
        this.value = value;
        this.predictionType = predictionType;
    }

    public String getName() {
        return name;
    }

    public VALUE getValue() {
        return value;
    }

    public PredictionType getPredictionType() {
        return predictionType;
    }


}
