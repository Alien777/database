package pl.lasota.tool.orm.service.listener;

public interface ListenerService<TYPE>  {

    void add(ChangeListener<TYPE> change);

    void remove(ChangeListener<TYPE>  change);
}
