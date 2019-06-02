package dms.dto.formatter;

import dms.dto.DescriptorTypeDto;
import dms.dto.DocumentTypeDto;
import dms.model.DescriptorType;
import dms.model.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentTypeDtoFormatter implements DtoFormatter<DocumentType, DocumentTypeDto> {

    @Autowired
    private DescriptorTypeDtoFormatter descriptorTypeDtoFormatter;

    @Override
    public DocumentTypeDto formatToDto(DocumentType entity) {
        if (entity == null) {
            return null;
        }
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        for (DescriptorType descriptorType : entity.getDescriptorTypes()) {
            dto.getDescriptorTypeDtos().add(descriptorTypeDtoFormatter.formatToDto(descriptorType));
        }
        return dto;
    }

    @Override
    public List<DocumentTypeDto> formatToDto(List<DocumentType> documentTypes) {
        List<DocumentTypeDto> documentTypeDtos = new ArrayList<>();
        for (DocumentType documentType : documentTypes) {
            documentTypeDtos.add(formatToDto(documentType));
        }
        return documentTypeDtos;
    }

    @Override
    public DocumentType formatToEntity(DocumentTypeDto dto) {
        if (dto == null) {
            return null;
        }
        DocumentType entity = new DocumentType();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        for (DescriptorTypeDto descriptorTypeDto :
                dto.getDescriptorTypeDtos()) {
            entity.getDescriptorTypes().add(descriptorTypeDtoFormatter.formatToEntity(descriptorTypeDto));
        }
        return entity;
    }

    @Override
    public List<DocumentType> formatToEntity(List<DocumentTypeDto> documentTypeDtos) {
        return null;
    }
}
