package pl.lasota.tool.sr.repository.query;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.query.field.*;
import pl.lasota.tool.sr.repository.query.normalize.AcccentNormalizer;
import pl.lasota.tool.sr.repository.query.normalize.LowerCaseNormalizer;
import pl.lasota.tool.sr.repository.query.normalize.Normalizable;
import pl.lasota.tool.sr.repository.query.normalize.UpperCaseNormalizer;
import pl.lasota.tool.sr.repository.query.sort.SimpleSort;
import pl.lasota.tool.sr.repository.query.sort.Sortable;


@ToString(callSuper = true)
@Data
public class CriteriaBuilderImpl implements BuilderQuery, CriteriaBuilder {


    private CriteriaBuilderImpl() {
    }

    public static CriteriaBuilderImpl criteria() {
        return new CriteriaBuilderImpl();
    }


    @Override
    public QueryCriteria build(Predicatable predicate, Pageable pageable) {
        return new QueryCriteria(predicate, null, pageable);
    }

    @Override
    public QueryCriteria build(Predicatable predicatable, Sortable sortable, Pageable pageable) {
        return new QueryCriteria(predicatable, sortable, pageable);
    }

    @Override
    public QueryDelete build(Predicatable predicatable) {
        return new QueryDelete(predicatable);
    }

    @Override
    public QueryUpdate build(Predicatable predicatable, Updatable updatable) {
        return new QueryUpdate(predicatable, updatable);
    }

    @Override
    public Predicatable and(Field... fields) {
        return new Predicate(fields, Operator.AND);
    }

    @Override
    public Predicatable or(Field... fields) {
        return new Predicate(fields, Operator.OR);
    }

    @Override
    public Predicatable root(Expression... expression) {
        return new Predicate(expression, Operator.ROOT);
    }

    @Override
    public Predicatable all() {
        return new Predicate((model, root, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1), Operator.ROOT);
    }

    @Override
    public Field equals(String path, Object object) {
        return new EqualField(path, object, ComparisonOperator.EQUAL);
    }

    @Override
    public Field notEquals(String path, Object object) {
        return new EqualField(path, object, ComparisonOperator.NOT_EQUAL);
    }

    @Override
    public Field lt(String path, Number number) {
        return new GreaterLessField(path, number, ComparisonOperator.LESS_THAN);

    }

    @Override
    public Field le(String path, Number number) {
        return new GreaterLessField(path, number, ComparisonOperator.LESS_THAN_OR_EQUAL);
    }

    @Override
    public Field gt(String path, Number number) {
        return new GreaterLessField(path, number, ComparisonOperator.GREATER_THAN);
    }

    @Override
    public Field ge(String path, Number number) {
        return new GreaterLessField(path, number, ComparisonOperator.GREATER_THAN_OR_EQUAL);
    }

    @Override
    public Field like(String path, String number) {
        return new LikeField(path, number, ComparisonOperator.LIKE);
    }

    @Override
    public Field likeR(String path, String number) {
        return new LikeField(path, number, ComparisonOperator.LIKE_RIGHT);
    }

    @Override
    public Field likeL(String path, String number) {
        return new LikeField(path, number, ComparisonOperator.LIKE_LEFT);
    }

    @Override
    public Field like(String path, String number, Normalizable... normalizable) {
        return new LikeField(path, number, ComparisonOperator.LIKE, normalizable);

    }

    @Override
    public Field likeR(String path, String number, Normalizable... normalizable) {
        return new LikeField(path, number, ComparisonOperator.LIKE_RIGHT, normalizable);

    }

    @Override
    public Field likeL(String path, String number, Normalizable... normalizable) {
        return new LikeField(path, number, ComparisonOperator.LIKE_LEFT, normalizable);

    }


    @Override
    public Field range(String path, Number min, Number max) {
        return new NumberRangeField(path, min, max);
    }


    @Override
    public Sortable sort(String path, Sortable.Sort sort) {
        return new SimpleSort(path, sort);
    }

    @Override
    public Sortable sort(String path) {
        return new SimpleSort(path);
    }

    @Override
    public Pageable page(int page) {
        return PageRequest.of(page, 10);
    }

    @Override
    public Pageable page(int page, int size) {
        return PageRequest.of(page, size);
    }


    @Override
    public Normalizable lowerCase() {
        return new LowerCaseNormalizer();
    }

    @Override
    public Normalizable upperCase() {
        return new UpperCaseNormalizer();
    }

    @Override
    public Normalizable accent() {
        return new AcccentNormalizer();
    }

    @Override
    public Updatable set(SetField... setField) {
        return new SetFields(setField);
    }
}
