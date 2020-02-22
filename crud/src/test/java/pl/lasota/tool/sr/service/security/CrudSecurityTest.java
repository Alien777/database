package pl.lasota.tool.sr.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.AccessContext;
import pl.lasota.tool.sr.security.Context;
import pl.lasota.tool.sr.service.base.BaseCrudService;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CrudSecurityTest {


    private static final String COLOR = "red";

    @Mock
    private BaseCrudService<Entit, Entit, Entit, Entit> baseCrudService;

    private ProvidingRules providingRules;

    private CrudSecurityService<Entit, Entit, Entit, Entit> crudSecurityService;

    private DozerMapper<Entit, Entit> mapper;

    @Before
    public void init() {
        providingRules = new DefaultProvidingRules();
        mapper = new DozerMapper<>(Entit.class);
        crudSecurityService = new CrudSecurityService<>(baseCrudService, mapper, providingRules);
    }

    @Test
    public void save() {
        when(baseCrudService.save(Mockito.any(Entit.class))).thenReturn(new Entit());

        Entit toSave = new Entit();
        toSave.setColor(COLOR);

        Context context = new Context();
        context.add(providingRules.create(a -> a.read().update().delete(), "admin"));

        crudSecurityService.save(toSave, context);

        assertThat(toSave).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(access -> access.getValue().equals("admin___7")));
    }

    @Test
    public void get() {
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin"));
        Context context = new Context();
        context.add(providingRules.create("admin"));

        Entit entit = crudSecurityService.get(1L, context);
        assertThat(entit).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(a -> a.getValue().equals("admin___4")));


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin"));
        context = new Context();
        context.add(providingRules.create("adamek"));

        entit = crudSecurityService.get(1L, context);
        assertThat(entit).isNull();


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::delete, "admin"));
        context = new Context();
        context.add(providingRules.create("admin"));

        entit = crudSecurityService.get(1L, context);
        assertThat(entit).isNull();

        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::update, "admin"));
        context = new Context();
        context.add(providingRules.create("admin"));

        entit = crudSecurityService.get(1L, context);
        assertThat(entit).isNull();

    }

    @Test
    public void delete() {
        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::delete, "admin"));

        Context context = new Context();
        context.add(providingRules.create("admin"));

        Long id = crudSecurityService.delete(1L, context);
        assertThat(id).isEqualTo(1L);


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::update, "admin"));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityService.delete(1L, context);
        assertThat(id).isNull();


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin"));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityService.delete(1L, context);
        assertThat(id).isNull();


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(a -> a.update().read().one(), "admin"));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityService.delete(1L, context);
        assertThat(id).isNull();

    }

    @Test
    public void update() {

        Entit entit = create(Accessible::update, "admin");
        when(baseCrudService.update(1L, entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);

        Context context = new Context();
        context.add(providingRules.create("admin"));

        Entit update = crudSecurityService.update(1L, entit, context);
        assertThat(update).isNotNull();


        entit = create(Accessible::delete, "admin");
        when(baseCrudService.update(1L, entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);

        context = new Context();
        context.add(providingRules.create("admin"));

        update = crudSecurityService.update(1L, entit, context);
        assertThat(update).isNull();

    }


    private Entit create(ConfigurationAccessible configurationAccessible, String name) {
        Entit inDatabase = new Entit();
        inDatabase.setColor(COLOR);


        Set<Access> accesses = new HashSet<>();
        AccessContext accessContext = providingRules.create(configurationAccessible, name);
        Access access = new Access();
        access.setRud(accessContext.getRud());
        access.setName(accessContext.getName());
        access.setValue(accessContext.getName() + Access.SEPARATOR + accessContext.getRud());
        accesses.add(access);
        inDatabase.setAccesses(accesses);
        return inDatabase;
    }
}
