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

    private ProvidingRules providingRules = new DefaultProvidingRules();

    private CrudSecurityService<Entit, Entit, Entit, Entit> crudSecurityService;

    @Before
    public void init() {
        crudSecurityService = new CrudSecurityService<>(baseCrudService, new DozerMapper<>(Entit.class), providingRules);
    }

    @Test
    public void saveWithPrivilege() {
        when(baseCrudService.save(Mockito.any(Entit.class))).thenReturn(new Entit());

        Entit toSave = new Entit();
        toSave.setColor(COLOR);
        String privilegeToCreate = providingRules.create(accessible -> accessible.delete().read().update(), "admin");
        crudSecurityService.save(toSave, privilegeToCreate);

        assertThat(toSave).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(access -> access.getPrivilege().equals("admin___7")));
    }

    @Test
    public void getIfDifferentPrivileges() {

        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));

        Entit entit = crudSecurityService.get(1L, "admin");

        assertThat(entit).matches(e -> e.getColor().equals(COLOR) &&
                e.getAccesses()
                        .stream()
                        .anyMatch(a -> a.getPrivilegeRud().equals("admin___4")));


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));
        entit = crudSecurityService.get(1L, "adamek");
        assertThat(entit).isNull();


        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::delete, "admin"));
        entit = crudSecurityService.get(1L, "admin");
        assertThat(entit).isNull();

        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::update, "admin"));
        entit = crudSecurityService.get(1L, "admin");
        assertThat(entit).isNull();
    }

    //
    @Test
    public void deleteIfDifferentPrivileges() {
        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::delete, "admin"));

        Long id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isEqualTo(1L);

        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::update, "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

        when(baseCrudService.delete(Mockito.anyLong())).thenReturn(1L);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(createEntity(a -> a.update().read().one(), "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

    }

    @Test
    public void updateIfDifferentPrivileges() {

        Entit entit = createEntity(Accessible::update, "admin");
        when(baseCrudService.update(1L, entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);


        Entit update = crudSecurityService.update(1L, entit, "admin");
        assertThat(update).isNotNull();


        entit = createEntity(Accessible::delete, "admin");
        when(baseCrudService.update(1L, entit)).thenReturn(entit);
        when(baseCrudService.get(Mockito.anyLong())).thenReturn(entit);

        update = crudSecurityService.update(1L, entit, "admin");
        assertThat(update).isNull();

    }

    private Entit createEntity(ConfigurationAccessible configurationAccessible, String privilege) {
        Entit entit = new Entit();
        entit.setColor(COLOR);
        Set<Access> accesses = new HashSet<>();
        String privilegeRud = providingRules.create(configurationAccessible, privilege);
        Access access = new Access(privilege, providingRules.rud(privilegeRud), privilegeRud);
        accesses.add(access);
        entit.setAccesses(accesses);
        return entit;
    }
}
