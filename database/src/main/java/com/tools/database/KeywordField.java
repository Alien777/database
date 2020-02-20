package com.tools.database;

public final class KeywordField implements BoolField<String> {

    private String field;
    private String value;
    private TypeBool typeBool;
    private BoolType typeQuery = BoolType.Phase;

    public static KeywordField must(String field, String value) {
        return new KeywordField(field, value, TypeBool.Must);
    }

    public static KeywordField notMust(String field, String value) {
        return new KeywordField(field, value, TypeBool.NotMust);
    }

    public static Field should(String field, String value) {
        return new KeywordField(field, value, TypeBool.Should);
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

    private KeywordField(String field, String value, TypeBool typeBool) {
        this.field = field;
        this.value = value;
        this.typeBool = typeBool;
    }
}
