package pl.lasota.tool.sr.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.security.SpecialPermission;
import pl.lasota.tool.sr.service.base.CrudAction;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CrudSecurityTest {


    private static final String COLOR = "red";

    @Mock
    private CrudAction<Entit, Entit, Entit, Entit> crudAction;

    private ProvidingPrivilege providingPrivilege = new SimpleProvidingPrivileges();

    private CrudSecurity<Entit, Entit, Entit, Entit> crudSecurityService;

    @Before
    public void init() {
        crudSecurityService = new CrudSecurity<>(crudAction, new DozerMapper<>(Entit.class), providingPrivilege);
    }

    @Test
    public void saveWithPrivilege() {
        when(crudAction.save(Mockito.any(Entit.class))).thenReturn(new Entit());

        Entit toSave = new Entit();
        toSave.setColor(COLOR);
        Short privilegeToCreate = providingPrivilege.create(accessible -> accessible.delete().read().update());
        pl.lasota.tool.sr.service.security.Auth admin = new Auth("admin", privilegeToCreate);
        crudSecurityService.save(toSave, admin);

        assertThat(toSave).matches(e -> e.getColor().equals(COLOR) &&
                e.getSpecialPermission()
                        .stream()
                        .anyMatch(access -> access.getPrivileged().equals("admin")));
    }

    @Test
    public void getIfDifferentPrivileges() {

        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));

        Entit entit = crudSecurityService.get(1L, "admin");

        assertThat(entit).matches(e -> e.getColor().equals(COLOR) &&
                e.getSpecialPermission()
                        .stream()
                        .anyMatch(a -> a.getPrivileged().equals("admin") && a.getPermission() == 4));


        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));
        entit = crudSecurityService.get(1L, "adamek");
        assertThat(entit).isNull();


        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::delete, "admin"));
        entit = crudSecurityService.get(1L, "admin");
        assertThat(entit).isNull();

        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::update, "admin"));
        entit = crudSecurityService.get(1L, "admin");
        assertThat(entit).isNull();
    }

    //
    @Test
    public void deleteIfDifferentPrivileges() {
        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::delete, "admin"));

        Long id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isEqualTo(1L);

        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::update, "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(a -> a.update().read().one(), "admin"));

        id = crudSecurityService.delete(1L, "admin");
        assertThat(id).isNull();

    }

    @Test
    public void updateIfDifferentPrivileges() {

        Entit entit = createEntity(Accessible::update, "admin");
        when(crudAction.update(1L, entit)).thenReturn(entit);
        when(crudAction.get(Mockito.anyLong())).thenReturn(entit);


        Entit update = crudSecurityService.update(1L, entit, "admin");
        assertThat(update).isNotNull();


        entit = createEntity(Accessible::delete, "admin");
        when(crudAction.update(1L, entit)).thenReturn(entit);
        when(crudAction.get(Mockito.anyLong())).thenReturn(entit);

        update = crudSecurityService.update(1L, entit, "admin");
        assertThat(update).isNull();

    }

    private Entit createEntity(ConfigurationAccessible configurationAccessible, String privilege) {
        Entit entit = new Entit();
        entit.setColor(COLOR);
        Set<SpecialPermission> specialPermissions = new HashSet<>();
        short privilegeRud = providingPrivilege.create(configurationAccessible);
        SpecialPermission specialPermission = new SpecialPermission(privilege, privilegeRud);
        specialPermissions.add(specialPermission);
        entit.setSpecialPermission(specialPermissions);
        return entit;
    }
}
