package com.tools.database;

public final class RangeBoolField implements RangeField {

    private String field;
    private Long from;
    private Long to;
    private TypeBool typeBool;
    private BoolType typeQuery = BoolType.Range;

    public static RangeBoolField must(String field, Long from, Long to) {
        return new RangeBoolField(field, from, to, TypeBool.Must);
    }

    public static RangeBoolField notMust(String field, Long from, Long to) {
        return new RangeBoolField(field, from, to, TypeBool.NotMust);
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
    public TypeBool getTypeBool() {
        return typeBool;
    }

    @Override
    public BoolType getTypeQuery() {
        return typeQuery;
    }

    private RangeBoolField(String field, Long from, Long to, TypeBool typeBool) {
        this.field = field;
        this.from = from;
        this.to = to;

        this.typeBool = typeBool;
    }

    @Override
    public Long from() {
        return from;
    }

    @Override
    public Long to() {
        return to;
    }
}
