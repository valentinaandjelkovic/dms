package dms.repository;

import dms.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public List<Company> findByName(String name);

    public List<Company> findByCompanyNumber(String companyNumber);
}
