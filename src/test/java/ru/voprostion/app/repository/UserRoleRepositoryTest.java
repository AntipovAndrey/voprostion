package ru.voprostion.app.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.domain.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleRepositoryTest {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private Role role1;
    private Role role2;

    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();


        role1 = new Role();
        role2 = new Role();

        role1.setName("role_1");
        role2.setName("role_2");

        roleRepository.save(role1);
        roleRepository.save(role2);

        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();

        user1.setName("user1");
        user1.setPassword("password1");

        user2.setName("user2");
        user2.setPassword("password1");

        user3.setName("user3");
        user3.setPassword("password1");

        user4.setName("user4");
        user4.setPassword("password1");

        user2.addRole(role1);

        user3.addRole(role2);

        user4.addRole(role1);
        user4.addRole(role2);
    }

    @Test
    public void save_user_saves_in_db() {
        final int sizeBefore = userRepository.findAll().size();
        userRepository.save(user1);
        final int sizeAfter = userRepository.findAll().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    public void one_role_saves_in_db_correctly() {
        userRepository.save(user2);
        final List<User> repositoryAll = userRepository.findAll();
        final User userFromDb = repositoryAll.get(0);
        final Set<Role> fromDbRoles = userFromDb.getRoles();

        Assert.assertEquals(1, fromDbRoles.size());
    }

    @Test
    public void many_roles_saves_in_db_correctly() {
        userRepository.save(user4);
        final User userFromDb = userRepository.findAll().get(0);
        final Set<Role> fromDbRoles = userFromDb.getRoles();

        Assert.assertEquals(2, fromDbRoles.size());
    }

    @Test
    public void remove_user_with_roles_does_not_remove_roles() {
        final User afterSave = userRepository.save(user4);
        userRepository.deleteById(afterSave.getId());
        final int sizeRoles = roleRepository.findAll().size();

        Assert.assertEquals(2, sizeRoles);
    }
}