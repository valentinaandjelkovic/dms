package dms.service;

import dms.model.DocumentType;

import java.util.List;

public interface DocumentTypeService {

    List<DocumentType> getAll();

    DocumentType save(DocumentType documentType);

    boolean deleteDescriptorType(Long documentTypeId, Long descriptorTypeId);

    public DocumentType getById(Long id);


}
