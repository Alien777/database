package pl.lasota.tool.orm.fulltextrepository.field;

import org.hibernate.search.spatial.Coordinates;
import pl.lasota.tool.orm.field.Condition;
import pl.lasota.tool.orm.field.CriteriaType;
import pl.lasota.tool.orm.common.Sort;
import pl.lasota.tool.orm.field.CriteriaField;


public class SortCoordinateField extends CriteriaField<Coordinates> {

    private final double distance;
    private final Sort sort;

    public SortCoordinateField(String name, Coordinates coordinates, double distance, Sort sort) {
        super(name, coordinates, CriteriaType.SORT);
        this.distance = distance;
        this.sort = sort;
    }

    public double getDistance() {
        return distance;
    }

    public Sort getSort() {
        return sort;
    }

    @Override
    public Condition condition() {
        return Condition.SORT;
    }
}