package com.tools.database;

public final class BelowBoolField implements BoolField<Long> {

    private String field;
    private Long value;
    private TypeBool typeBool;
    private BoolType typeQuery = BoolType.Below;

    public static BelowBoolField must(String field, Long value) {
        return new BelowBoolField(field, value, TypeBool.Must);
    }

    public static BelowBoolField notMust(String field, Long value) {
        return new BelowBoolField(field, value, TypeBool.NotMust);
    }


    @Override
    public String getField() {
        return field;
    }

    @Override
    public String[] getFields() {
        return new String[]{field};
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public TypeBool getTypeBool() {
        return typeBool;
    }

    @Override
    public BoolType getTypeQuery() {
        return typeQuery;
    }

    private BelowBoolField(String field, Long value, TypeBool typeBool) {
        this.field = field;
        this.value = value;
        this.typeBool = typeBool;
    }
}
