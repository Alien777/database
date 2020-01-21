package pl.lasota.tool.sr.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.AccessContext;
import pl.lasota.tool.sr.security.Context;
import pl.lasota.tool.sr.service.base.BaseCrudService;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrudSecurityDelegatorTest {


    private static final String COLOR = "red";

    @Mock
    private BaseCrudService<Entit, Entit, Entit, Entit> baseCrudService;

    private ProvidingRules providingRules;

    private CrudSecurityDelegator<Entit, Entit, Entit, Entit> crudSecurityDelegator;

    @Before
    public void init() {
        providingRules = new DefaultProvidingRules();
        crudSecurityDelegator = new CrudSecurityDelegator<>(baseCrudService, providingRules);
    }

    @Test
    public void save() {
        when(baseCrudService.save(Mockito.any(Entit.class))).thenReturn(new Entit());

        Entit toSave = new Entit();
        toSave.setColor(COLOR);

        Context context = new Context();
        context.add(providingRules.create(a -> a.read().update().delete(), "admin"));

        crudSecurityDelegator.save(toSave, context);

        assertThat(toSave).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(access -> access.getValue().equals("admin___7")));
    }

    @Test
    public void get() {
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin", 1L));
        Context context = new Context();
        context.add(providingRules.create("admin"));

        Entit entit = crudSecurityDelegator.get(1L, context);
        assertThat(entit).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(a -> a.getValue().equals("admin___4")));


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin", 1L));
        context = new Context();
        context.add(providingRules.create("adamek"));

        entit = crudSecurityDelegator.get(1L, context);
        assertThat(entit).isNull();


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::delete, "admin", 1L));
        context = new Context();
        context.add(providingRules.create("admin"));

        entit = crudSecurityDelegator.get(1L, context);
        assertThat(entit).isNull();

        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::update, "admin", 1L));
        context = new Context();
        context.add(providingRules.create("admin"));

        entit = crudSecurityDelegator.get(1L, context);
        assertThat(entit).isNull();

    }

    @Test
    public void delete() {
        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::delete, "admin", 1L));

        Context context = new Context();
        context.add(providingRules.create("admin"));

        Long id = crudSecurityDelegator.delete(1L, context);
        assertThat(id).isEqualTo(1L);


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::update, "admin", 1L));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityDelegator.delete(1L, context);
        assertThat(id).isNull();


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(Accessible::read, "admin", 1L));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityDelegator.delete(1L, context);
        assertThat(id).isNull();


        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(create(a -> a.update().read().one(), "admin", 1L));

        context = new Context();
        context.add(providingRules.create("admin"));

        id = crudSecurityDelegator.delete(1L, context);
        assertThat(id).isNull();

    }

    @Test
    public void update() {

        Entit entit = create(Accessible::update, "admin", 1L);
        when(baseCrudService.update(entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);

        Context context = new Context();
        context.add(providingRules.create("admin"));

        Entit update = crudSecurityDelegator.update(entit, context);
        assertThat(update).isNotNull();


        entit = create(Accessible::delete, "admin", 1L);
        when(baseCrudService.update(entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);

        context = new Context();
        context.add(providingRules.create("admin"));

        update = crudSecurityDelegator.update(entit, context);
        assertThat(update).isNull();

    }


    private Entit create(ConfigurationAccessible configurationAccessible, String name, Long id) {
        Entit inDatabase = new Entit();
        inDatabase.setId(id);
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
