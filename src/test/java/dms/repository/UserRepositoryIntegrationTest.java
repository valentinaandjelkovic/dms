package dms.repository;

import dms.config.database.HibernateConfiguration;
import dms.model.Company;
import dms.model.Role;
import dms.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = HibernateConfiguration.class)
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSave() {
        Company company = new Company(null, "Test", "123456788");
        company = companyRepository.save(company);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.save(new Role(null, "ROLE_ADMIN")));
        User user = new User(null, "Valentina", "Andjelkovic", "valentina", "valentina", company, roles);
        user = userRepository.save(user);


        assertNotNull(user.getId());
        assertTrue(user.getId() > 0);
        assertEquals("Valentina", user.getFirstName());
        assertEquals("Andjelkovic", user.getLastName());
        assertEquals("valentina", user.getUsername());
    }

    @Test
    public void testFindByUsername() {
        Company company = new Company(null, "Test", "123456788");
        company = companyRepository.save(company);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.save(new Role(null, "ROLE_DOCUMENT_MANAGER")));
        User user = new User(null, "Valentina", "Andjelkovic", "valentina", "valentina", company, roles);
        user = userRepository.save(user);

        user = userRepository.findByUsername("valentina");
        assertNotNull(user);
        assertEquals("Valentina", user.getFirstName());
        assertEquals("Andjelkovic", user.getLastName());
        assertEquals("valentina", user.getUsername());
    }
}
