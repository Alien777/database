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
import pl.lasota.tool.sr.mapping.DozerSameObject;
import pl.lasota.tool.sr.repository.crud.SimpleCrudRepository;
import pl.lasota.tool.sr.security.SpecialPermission;

import java.util.HashSet;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CrudActionTest {

    @Mock
    private DozerMapper<Entit, Entit> dozerMapper;

    @Mock
    private DozerMapper<Entit, Entit> updateToModel;

    @Mock
    private SimpleCrudRepository<Entit> crudRepository;

    private CrudAction<Entit, Entit, Entit, Entit> crudAction;

    @Before
    public void init() {
        crudAction = new CrudAction<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
    }

    @Test
    public void save() {
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(new Entit());
        Mockito.when(crudRepository.save(Mockito.any())).thenReturn(null);
        Entit save = crudAction.save(new Entit());
        assertThat(save).isNull();


        Entit entit = new Entit();
        entit.setColor("red");
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(entit);
        Mockito.when(crudRepository.save(Mockito.any())).thenReturn(entit);
        save = crudAction.save(new Entit());
        assertThat(save).isNotNull()
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("red"));

    }

    @Test
    public void get() {

        Mockito.when(crudRepository.get(1L)).thenReturn(null);
        Entit get = crudAction.get(1L);
        assertThat(get).isNull();


        Entit entit = new Entit();
        entit.setColor("red");
        Mockito.when(dozerMapper.mapper(Mockito.any())).thenReturn(entit);
        Mockito.when(crudRepository.get(1L)).thenReturn(entit);
        get = crudAction.get(1L);
        assertThat(get).isNotNull()
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("red"));
    }

    @Test
    public void delete() {
        Mockito.when(crudRepository.delete(1L)).thenReturn(null);
        Long delete = crudAction.delete(1L);
        assertThat(delete).isNull();


        Mockito.when(crudRepository.delete(1L)).thenReturn(1L);
        delete = crudAction.delete(1L);
        assertThat(delete).isEqualTo(1L);
    }

    @Test
    public void update() {
        Entit entit = new Entit();

        Entit updated = crudAction.update(0L, entit);
        assertThat(updated).isNull();

        entit = new Entit();
        updated = crudAction.update(-1L, entit);
        assertThat(updated).isNull();

        entit = new Entit();
        updated = crudAction.update(null, entit);
        assertThat(updated).isNull();


        //////////////////////
        Entit getUpdate = new Entit();
        getUpdate.setColor("red");
        Mockito.when(crudRepository.get(1L)).thenReturn(getUpdate);

        Entit toUpdating = new Entit();
        toUpdating.setId(1L);
        toUpdating.setColor("blue");

        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toUpdating);


        crudAction = new CrudAction<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        ArgumentCaptor<Entit> argument = ArgumentCaptor.forClass(Entit.class);
        crudAction.update(1L, toUpdating);

        Mockito.verify(dozerMapper).mapper(argument.capture());

        assertThat(argument.getValue())
                .isInstanceOfSatisfying(Entit.class, a -> assertThat(a.getColor()).isEqualTo("blue"));

        Mockito.reset(dozerMapper, crudRepository, updateToModel);

    }

    @Test
    public void updateTest() {

        Entit toUpdate = new Entit();
        toUpdate.setColor("NOWY KOLOR");
        toUpdate.setId(3L);
        HashSet<SpecialPermission> toUpdateSpecialPermissions = new HashSet<>();
        toUpdateSpecialPermissions.add(new SpecialPermission("role", (short) 4));
        toUpdate.setSpecialPermissions(toUpdateSpecialPermissions);

        Entit toOld = new Entit();
        toOld.setId(1L);
        toOld.setColor("STARY  KOLOR");
        HashSet<SpecialPermission> toOldSpecialPermissions = new HashSet<>();
        toOldSpecialPermissions.add(new SpecialPermission("stary_role", (short) 5));
        toOld.setSpecialPermissions(toOldSpecialPermissions);


        DozerSameObject<Entit> object = new DozerSameObject<>(Entit.class);
        object.mapper(toUpdate, toOld);

        assertThat(toOld.getColor()).isEqualTo("NOWY KOLOR");
        assertThat(toOld.getId()).isEqualTo(1L);
        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, String>) SpecialPermission::getPrivileged)
                .contains("stary_role");

        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, Short>) SpecialPermission::getPermission)
                .contains((short) 5);

        crudAction = new CrudAction<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        Mockito.when(crudRepository.get(1L)).thenReturn(toOld);
        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toOld);

        ArgumentCaptor<Entit> argument = ArgumentCaptor.forClass(Entit.class);
        crudAction.update(1L, toUpdate);
        Mockito.verify(dozerMapper).mapper(argument.capture());


        assertThat(argument.getValue().getColor()).isEqualTo("NOWY KOLOR");
        assertThat(argument.getValue().getId()).isEqualTo(1L);
        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, String>) SpecialPermission::getPrivileged)
                .contains("stary_role");

        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, Short>) SpecialPermission::getPermission)
                .contains((short) 5);
    }
}
