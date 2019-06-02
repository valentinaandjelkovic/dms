package dms.datatables;

import dms.model.Company;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDataTablesRepository extends DataTablesRepository<Company, Long> {
}
