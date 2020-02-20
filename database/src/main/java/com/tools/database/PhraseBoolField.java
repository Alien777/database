package com.tools.database;

public final class PhraseBoolField implements BoolField<String> {

    private String field;
    private String value;
    private TypeBool typeBool;
    private BoolType typeQuery = BoolType.Phase;

    public static PhraseBoolField must(String field, String value) {
        return new PhraseBoolField(field, value, TypeBool.Must);
    }

    public static PhraseBoolField notMust(String field, String value) {
        return new PhraseBoolField(field, value, TypeBool.NotMust);
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
    public String getValue() {
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

    private PhraseBoolField(String field, String value, TypeBool typeBool) {
        this.field = field;
        this.value = value;
        this.typeBool = typeBool;
    }
}
