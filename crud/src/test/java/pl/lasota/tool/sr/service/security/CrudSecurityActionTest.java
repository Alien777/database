package pl.lasota.tool.sr.service.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.security.Entitlement;
import pl.lasota.tool.sr.security.SpecialPermission;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CrudSecurityActionTest {


    private static final String COLOR = "red";
    private final ProvidingPrivilege providingPrivilege = new SimpleProvidingPrivileges();


    @Test
    public void saveWithPrivilege() {
        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            return new Context("admindsadsada", "ADMINdsadsa", stringList);
        };
        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.save(Mockito.any(Entit.class))).thenReturn(new Entit());

        Entit toSave = new Entit();
        toSave.setColor(COLOR);
        crud.save(toSave, new SecureInit("ADMIN", "admin"));

        assertThat(toSave).matches(e -> e.getColor().equals(COLOR)
                && e.getEntitlement().getGroup().equals("admin")
                && e.getEntitlement().getOwner().equals("ADMIN")
                && e.getEntitlement().getPermission() == (short) 1700
                && e.getEntitlement().getSpecialPermissions().isEmpty());
    }

    @Test
    public void getEntityIfUserCanRead() {


        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            return new Context("admin", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage", "admin", "ADMIN", (short) 1700));

        Entit entit = crud.get(1L);

        assertThat(entit).matches(e -> e.getColor().equals(COLOR));

    }

    @Test
    public void getEntityIdUserNotAccessToRead() {


        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            return new Context("admin", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage", "admin", "ADMIN", (short) 1100));

        Entit entit = crud.get(1L);

        assertThat(entit).isNull();

    }

    @Test
    public void getEntityIfGroupCanRead() {

        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            return new Context("admin", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage", "admin", "ADMIN", (short) 1170));

        Entit entit = crud.get(1L);

        assertThat(entit).isNotNull();

    }

    @Test
    public void getEntityIfUserHasSpecialPrivilegeToRead() {

        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            stringList.add("privilage");
            return new Context("admin", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage", "admin", "ADMIN", (short) 1000));

        Entit entit = crud.get(1L);

        assertThat(entit).isNotNull();

    }


    @Test
    public void getEntityIfNotHasPermission() {

        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            stringList.add("privilage");
            return new Context("admin1", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);

        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage1", "admin", "ADMIN", (short) 1111));

        Entit entit = crud.get(1L);

        assertThat(entit).isNull();

    }


    @Test
    public void updateEntityIfNotHasPermission() {

        ProvidingContext providingContext = () -> {
            List<String> stringList = new LinkedList<>();
            stringList.add("privilage");
            return new Context("admin1", "ADMIN", stringList);
        };

        CrudRepository<Entit> repo = (CrudRepository<Entit>) Mockito.mock(CrudRepository.class);
        CrudSecurityAction<Entit, Entit, Entit, Entit> crud =
                new CrudSecurityAction<>(repo,
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        new DozerMapper<>(Entit.class),
                        providingContext,
                        providingPrivilege,
                        Entit.class);

        when(repo.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "privilage1", "admin", "ADMIN", (short) 1170));
        when(repo.update(Mockito.any(Entit.class))).thenReturn(Mockito.any(Entit.class));

        Entit tuUpdate = new Entit();
        tuUpdate.setEntitlement(new Entitlement());
        tuUpdate.getEntitlement().setPermission((short) 666);
        tuUpdate.setColor("WHITE");
        tuUpdate.getEntitlement().setGroup("AAAAAAAAAAAAAAAAAA");
        Entit entit = crud.update(1L, tuUpdate);

        assertThat(entit).isNotNull().matches(e -> e.getEntitlement().getPermission() == 1170);

    }


//    @Test
//    public void deleteIfDifferentPrivileges() {
//        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::delete, "admin"));
//
//        Long id = crudSecurityActionService.delete(1L, "admin");
//        assertThat(id).isEqualTo(1L);
//
//        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::update, "admin"));
//
//        id = crudSecurityActionService.delete(1L, "admin");
//        assertThat(id).isNull();
//
//        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(Accessible::read, "admin"));
//
//        id = crudSecurityActionService.delete(1L, "admin");
//        assertThat(id).isNull();
//
//        when(crudAction.delete(Mockito.anyLong())).thenReturn(1L);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(createEntity(a -> a.update().read().one(), "admin"));
//
//        id = crudSecurityActionService.delete(1L, "admin");
//        assertThat(id).isNull();
//
//    }
//
//    @Test
//    public void updateIfDifferentPrivileges() {
//
//        Entitling entit = createEntity(Accessible::update, "admin");
//        when(crudAction.update(1L, entit)).thenReturn(entit);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(entit);
//
//
//        Entitling update = crudSecurityActionService.update(1L, entit, "admin");
//        assertThat(update).isNotNull();
//
//
//        entit = createEntity(Accessible::delete, "admin");
//        when(crudAction.update(1L, entit)).thenReturn(entit);
//        when(crudAction.get(Mockito.anyLong())).thenReturn(entit);
//
//        update = crudSecurityActionService.update(1L, entit, "admin");
//        assertThat(update).isNull();
//
//    }

    private Entit createEntity(ConfigurationAccessible configurationAccessible, String privilege, String username, String group, Short priv) {
        Entit entit = new Entit();
        entit.setEntitlement(new Entitlement());
        entit.setColor(COLOR);
        if (privilege != null) {
            Set<SpecialPermission> specialPermissions = new HashSet<>();
            short privilegeRud = providingPrivilege.create(configurationAccessible);
            SpecialPermission specialPermission = new SpecialPermission(privilege, privilegeRud);
            specialPermissions.add(specialPermission);
            entit.getEntitlement().setSpecialPermissions(specialPermissions);
        }
        if (username != null)
            entit.getEntitlement().setOwner(username);

        if (group != null)
            entit.getEntitlement().setGroup(group);

        if (priv != null)
            entit.getEntitlement().setPermission(priv);

        return entit;
    }
}
