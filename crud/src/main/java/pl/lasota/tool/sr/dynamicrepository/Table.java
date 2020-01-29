package pl.lasota.tool.sr.dynamicrepository;

public interface Table extends Buildable {

    String addColumn();

    String removeColumn();
}
