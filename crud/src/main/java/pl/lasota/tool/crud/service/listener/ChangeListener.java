package pl.lasota.tool.crud.service.listener;

public interface ChangeListener<TYPE>  {

    void onChange(TYPE object,TypeListener typeListener);
}
