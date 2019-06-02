package dms.datatables;

import dms.model.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataTablesRepository extends DataTablesRepository<User, Long> {
}
