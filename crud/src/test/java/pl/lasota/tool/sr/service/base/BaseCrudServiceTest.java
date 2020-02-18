package pl.lasota.tool.sr.service.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.SimpleCrudRepository;
import pl.lasota.tool.sr.security.Access;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BaseCrudServiceTest {

    @Mock
    private DozerMapper<Entit, Entit> dozerMapper;

    @Mock
    private DozerMapper<Entit, Entit> updateToModel;

    @Mock
    private SimpleCrudRepository<Entit> crudRepository;

    private BaseCrudService<Entit, Entit, Entit, Entit> baseCrudService;

    @Before
    public void init() {
        baseCrudService = new BaseCrudService<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
    }

    @Test
    public void save() {
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(new Entit());
        Mockito.when(crudRepository.save(Mockito.any())).thenReturn(null);
        Entit save = baseCrudService.save(new Entit());
        assertThat(save).isNull();


        Entit entit = new Entit();
        entit.setColor("red");
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(entit);
        Mockito.when(crudRepository.save(Mockito.any())).thenReturn(entit);
        save = baseCrudService.save(new Entit());
        assertThat(save).isNotNull()
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("red"));

    }

    @Test
    public void get() {

        Mockito.when(crudRepository.get(1L)).thenReturn(null);
        Entit get = baseCrudService.get(1L);
        assertThat(get).isNull();


        Entit entit = new Entit();
        entit.setColor("red");
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(entit);
        Mockito.when(crudRepository.get(1L)).thenReturn(entit);
        get = baseCrudService.get(1L);
        assertThat(get).isNotNull()
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("red"));
    }

    @Test
    public void delete() {
        Mockito.when(crudRepository.delete(1L)).thenReturn(null);
        Long delete = baseCrudService.delete(1L);
        assertThat(delete).isNull();


        Mockito.when(crudRepository.delete(1L)).thenReturn(1L);
        delete = baseCrudService.delete(1L);
        assertThat(delete).isEqualTo(1L);
    }

    @Test
    public void update() {
        Entit entit = new Entit();
        entit.setId(0L);
        Entit updated = baseCrudService.update(entit);
        assertThat(updated).isNull();

        entit = new Entit();
        entit.setId(-1L);
        updated = baseCrudService.update(entit);
        assertThat(updated).isNull();

        entit = new Entit();
        updated = baseCrudService.update(entit);
        assertThat(updated).isNull();


        //////////////////////
        Entit getUpdate = new Entit();
        getUpdate.setId(1L);
        getUpdate.setColor("red");
        Mockito.when(crudRepository.get(1L)).thenReturn(getUpdate);

        Entit toUpdating = new Entit();
        toUpdating.setId(1L);
        toUpdating.setColor("blue");

        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toUpdating);


        baseCrudService = new BaseCrudService<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        ArgumentCaptor<Entit> argument = ArgumentCaptor.forClass(Entit.class);
        baseCrudService.update(toUpdating);

        Mockito.verify(dozerMapper).mapper(argument.capture());

        assertThat(argument.getValue())
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("blue"));

        Mockito.reset(dozerMapper, crudRepository, updateToModel);
//////////////////////////
        getUpdate = new Entit();
        getUpdate.setId(1L);
        getUpdate.setColor("red");
        HashSet<Access> accesses = new HashSet<>();

        Access access = new Access();
        access.setName("adamek");
        access.setValue("adamek___1");
        access.setRud((short) 1);
        access.setId(2L);
        accesses.add(access);
        getUpdate.setAccesses(accesses);


        Mockito.when(crudRepository.get(1L)).thenReturn(getUpdate);

        toUpdating = new Entit();
        toUpdating.setId(1L);
        toUpdating.setColor(null);

        accesses = new HashSet<>();
        access = new Access();
        access.setName("adam");
        access.setValue("adam___1");
        access.setRud((short) 1);
        access.setId(2L);
        accesses.add(access);

        toUpdating.setAccesses(accesses);

        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toUpdating);

        baseCrudService = new BaseCrudService<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        argument = ArgumentCaptor.forClass(Entit.class);

        baseCrudService.update(toUpdating);

        Mockito.verify(dozerMapper).mapper(argument.capture());
        assertThat(argument.getValue())
                .isInstanceOfSatisfying(Entit.class, a -> {
                    assertThat(a.getAccesses()).hasSize(1).element(0)
                            .isInstanceOfSatisfying(Access.class, b ->
                                    assertThat(b.getValue()).isEqualTo("adamek___1"));
                    assertThat(a.getColor()).isEqualTo("red");
                });
    }
}
