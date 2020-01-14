package pl.lasota.tool.sr.field;

import org.hibernate.search.spatial.Coordinates;


public class SortCoordinateField extends CriteriaField<Coordinates> {

    private final double distance;
    private final Sort sort;

    public SortCoordinateField(String name, Coordinates coordinates, double distance, Sort sort) {
        super(name, coordinates, Selector.SORT);
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
    public Operator condition() {
        return Operator.SORT;
    }
}
