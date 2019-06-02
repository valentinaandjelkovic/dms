package dms.service.impl;

import dms.dto.DocumentDto;
import dms.dto.formatter.DocumentDtoFormatter;
import dms.dto.validator.DocumentDtoSaveValidator;
import dms.model.Document;
import dms.repository.DocumentRepository;
import dms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentDtoSaveValidator saveValidator;

    @Autowired
    private DocumentDtoFormatter dtoFormatter;


    @Override
    public Document getById(Long id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document save(DocumentDto documentDto) throws Exception {
        saveValidator.validate(documentDto);
        documentDto.setDate(new Date());
        return documentRepository.save(dtoFormatter.formatToEntity(documentDto));
    }
}
