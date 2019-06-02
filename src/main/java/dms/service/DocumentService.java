package dms.service;

import dms.dto.DocumentDto;
import dms.model.Document;

import java.util.List;

public interface DocumentService {

    public Document getById(Long id);

    List<Document> getAll();

    Document save(DocumentDto documentDto) throws Exception;
}
