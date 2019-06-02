package dms.service.impl;

import dms.model.DescriptorType;
import dms.model.DocumentType;
import dms.repository.DescriptorTypeRepository;
import dms.repository.DocumentTypeRepository;
import dms.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private DescriptorTypeRepository descriptorTypeRepository;

    @Override
    public List<DocumentType> getAll() {
        return documentTypeRepository.findAll();
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    @Override
    public boolean deleteDescriptorType(Long documentTypeId, Long descriptorTypeId) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId).get();
        DescriptorType descriptorType = descriptorTypeRepository.findById(descriptorTypeId).get();
        if (documentType == null || descriptorType == null || !documentType.getDescriptorTypes().contains(descriptorType)) {
            return false;
        }
        try {
            documentType.getDescriptorTypes().remove(descriptorType);
            documentTypeRepository.save(documentType);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public DocumentType getById(Long id) {
        return documentTypeRepository.findById(id).get();
    }
}
