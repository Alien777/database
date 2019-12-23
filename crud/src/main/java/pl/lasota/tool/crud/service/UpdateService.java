package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.serach.field.Field;

import java.util.List;

public interface  UpdateService<READING> {

    List<READING> update(List<Field<?>> source);

}
