package pl.lasota.tool.orm.service.listener;

public interface ChangeListener<TYPE>  {

    void onChange(TYPE object,TypeListener typeListener);
}
