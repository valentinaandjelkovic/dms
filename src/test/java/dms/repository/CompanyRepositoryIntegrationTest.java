package dms.repository;

import dms.config.database.HibernateConfiguration;
import dms.model.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = HibernateConfiguration.class)
@Transactional
public class CompanyRepositoryIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSave() {
        Company company = new Company(null, "Test", "123456788");

        assertNull(company.getId());

        company = companyRepository.save(company);

        System.out.println("Company id " + company.getId());
        assertNotNull(company.getId());
        assertTrue(company.getId() > 0);
    }

    @Test
    public void testfindByName() {
        Company company = new Company(null, "Test 2", "123456789");
        company = companyRepository.save(company);

        System.out.println("Company id " + company.getId());

        List<Company> result = companyRepository.findByName("Test 2");
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0));
        assertEquals(result.get(0).getId(), company.getId());
    }

    @Test
    public void testFindByCompanyNumber(){
        Company company = new Company(null, "Test 3", "123456788");
        company = companyRepository.save(company);

        List<Company> result = companyRepository.findByCompanyNumber("123456788");
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0));
        assertEquals(result.get(0).getId(), company.getId());
    }

    public void testDeleteById(){
        companyRepository.deleteById(2l);
        Company company = companyRepository.getOne(2l);

        assertNull(company);
    }

}
