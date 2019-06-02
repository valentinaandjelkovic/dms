package dms.dto.formatter;

import dms.dto.DocumentDto;
import dms.model.Document;
import dms.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentDtoFormatter implements DtoFormatter<Document, DocumentDto> {
    @Autowired
    private DocumentTypeDtoFormatter documentTypeDtoFormatter;
    @Autowired
    private DescriptorDtoFormatter descriptorDtoFormatter;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentDto formatToDto(Document document) {
        if (document == null) {
            return null;
        }
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(document.getId());
        documentDto.setName(document.getName());
        documentDto.setDate(document.getDate());
        documentDto.setFile(document.getFile());
        documentDto.setFileType(document.getFileType());
        documentDto.setDocumentTypeId(document.getType().getId());
        documentDto.setFileName(document.getFileName());
        documentDto.setTypeDto(documentTypeDtoFormatter.formatToDto(document.getType()));
        documentDto.setDescriptorDtoList(descriptorDtoFormatter.formatToDto(document.getDescriptorList()));

        return documentDto;
    }

    @Override
    public List<DocumentDto> formatToDto(List<Document> documents) {
        List<DocumentDto> documentDtos = new ArrayList<>();
        for (Document document : documents) {
            documentDtos.add(formatToDto(document));
        }
        return documentDtos;
    }

    @Override
    public Document formatToEntity(DocumentDto dto) {
        if (dto == null) {
            return null;
        }
        Document document = new Document();
        document.setId(dto.getId());
        document.setDate(dto.getDate());
        document.setName(dto.getName());
        document.setFile(dto.getFile());
        document.setFileType(dto.getFileType());
        document.setType(documentTypeRepository.findById(dto.getDocumentTypeId()).get());
        document.setFileName(dto.getFileName());
        document.setDescriptorList(descriptorDtoFormatter.formatToEntity(dto.getDescriptorDtoList()));
        return document;
    }

    @Override
    public List<Document> formatToEntity(List<DocumentDto> documentDtos) {
        List<Document> documents = new ArrayList<>();
        for (DocumentDto documentDto : documentDtos) {
            documents.add(formatToEntity(documentDto));
        }
        return documents;
    }
}
