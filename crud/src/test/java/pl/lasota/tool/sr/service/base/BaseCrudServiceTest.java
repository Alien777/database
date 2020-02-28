package pl.lasota.tool.sr.service.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.helper.ObjectTest;
import pl.lasota.tool.sr.helper.TestNotMapping;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.DozerSameObject;
import pl.lasota.tool.sr.repository.crud.SimpleCrudRepository;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.service.security.DefaultProvidingRules;

import java.util.HashSet;
import java.util.function.Function;

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

        Entit updated = baseCrudService.update(0L, entit);
        assertThat(updated).isNull();

        entit = new Entit();
        updated = baseCrudService.update(-1L, entit);
        assertThat(updated).isNull();

        entit = new Entit();
        updated = baseCrudService.update(null, entit);
        assertThat(updated).isNull();


        //////////////////////
        Entit getUpdate = new Entit();
        getUpdate.setColor("red");
        Mockito.when(crudRepository.get(1L)).thenReturn(getUpdate);

        Entit toUpdating = new Entit();
        toUpdating.setId(1L);
        toUpdating.setColor("blue");

        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toUpdating);


        baseCrudService = new BaseCrudService<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        ArgumentCaptor<Entit> argument = ArgumentCaptor.forClass(Entit.class);
        baseCrudService.update(1L, toUpdating);

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
        HashSet<Access> toUpdateAccesses = new HashSet<>();
        toUpdateAccesses.add(new Access("role", (short) 4, new DefaultProvidingRules().create("stary_role", (short) 4)));
        toUpdate.setAccesses(toUpdateAccesses);

        Entit toOld = new Entit();
        toOld.setId(1L);
        toOld.setColor("STARY  KOLOR");
        HashSet<Access> toOldAccesses = new HashSet<>();
        toOldAccesses.add(new Access("stary_role", (short) 5, new DefaultProvidingRules().create("stary_role", (short) 5)));
        toUpdate.setAccesses(toOldAccesses);


        DozerSameObject<Entit> object = new DozerSameObject<>(Entit.class);
        object.mapper(toUpdate, toOld);

        assertThat(toOld.getColor()).isEqualTo("NOWY KOLOR");
        assertThat(toOld.getId()).isEqualTo(1L);
        assertThat(toOld.getAccesses())
                .hasSize(1)
                .extracting((Function<Access, String>) Access::getPrivilegeRud)
                .contains("stary_role___5");

        baseCrudService = new BaseCrudService<>(crudRepository, dozerMapper, updateToModel, dozerMapper, Entit.class);
        Mockito.when(crudRepository.get(1L)).thenReturn(toOld);
        Mockito.when(updateToModel.mapper(Mockito.any())).thenReturn(toOld);

        ArgumentCaptor<Entit> argument = ArgumentCaptor.forClass(Entit.class);
        baseCrudService.update(1L, toUpdate);
        Mockito.verify(dozerMapper).mapper(argument.capture());


        assertThat(argument.getValue().getColor()).isEqualTo("NOWY KOLOR");
        assertThat(argument.getValue().getId()).isEqualTo(1L);
        assertThat(argument.getValue().getAccesses())
                .hasSize(1)
                .extracting((Function<Access, String>) Access::getPrivilegeRud)
                .contains("stary_role___5");
    }
}
