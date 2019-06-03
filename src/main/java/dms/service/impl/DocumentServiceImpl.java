package dms.service.impl;

import dms.dto.DocumentDto;
import dms.dto.formatter.DocumentDtoFormatter;
import dms.dto.validator.DocumentDtoSaveValidator;
import dms.model.Activity;
import dms.model.Document;
import dms.repository.ActivityRepository;
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

    @Autowired
    private ActivityRepository activityRepository;


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
        Document document = documentRepository.save(dtoFormatter.formatToEntity(documentDto));
        if (document != null) {
            if (documentDto.getInputActivityId() != null) {
                Activity activity = activityRepository.findById(documentDto.getInputActivityId()).get();
                activity.getInputDocuments().add(document);
                activityRepository.save(activity);
            }
            if (documentDto.getOutputActivityId() != null) {
                Activity activity = activityRepository.findById(documentDto.getOutputActivityId()).get();
                activity.getOutputDocuments().add(document);
                activityRepository.save(activity);
            }
        }
        return document;
    }
}
