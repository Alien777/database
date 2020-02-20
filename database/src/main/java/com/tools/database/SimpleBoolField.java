package com.tools.database;

public final class SimpleBoolField implements BoolField<String> {

    private String[] field;
    private String value;
    private TypeBool typeBool;
    private BoolType typeQuery = BoolType.SimpleQuery;

    public static SimpleBoolField must(String[] field, String value) {
        return new SimpleBoolField(field, value, TypeBool.Must);
    }

    public static SimpleBoolField notMust(String[] field, String value) {
        return new SimpleBoolField(field, value, TypeBool.NotMust);
    }

    public static SimpleBoolField must(String field, String value) {
        return new SimpleBoolField(new String[]{field}, value, TypeBool.Must);
    }

    public static SimpleBoolField notMust(String field, String value) {
        return new SimpleBoolField(new String[]{field}, value, TypeBool.NotMust);
    }

    public static SimpleBoolField should(String[] field, String value) {
        return new SimpleBoolField(field, value, TypeBool.Should);
    }

    public static SimpleBoolField should(String field, String value) {
        return new SimpleBoolField(new String[]{field}, value, TypeBool.Should);
    }

    public String[] getFields() {
        return field;
    }

    @Override
    public String getField() {
        return field[0];
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

    private SimpleBoolField(String[] field, String value, TypeBool typeBool) {
        this.field = field;
        this.value = value;
        this.typeBool = typeBool;
    }
}
