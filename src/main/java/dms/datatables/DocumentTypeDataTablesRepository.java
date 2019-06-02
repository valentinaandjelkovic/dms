package dms.datatables;

import dms.model.DocumentType;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeDataTablesRepository extends DataTablesRepository<DocumentType, Long> {
}
