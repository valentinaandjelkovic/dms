package dms.datatables;

import dms.model.Activity;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDataTablesRepository extends DataTablesRepository<Activity, Long> {
}
