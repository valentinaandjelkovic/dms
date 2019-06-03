package dms.dto.validator;

import dms.dto.DescriptorDto;
import dms.dto.DocumentDto;
import dms.exception.DmsException;
import dms.model.DescriptorType;
import dms.model.DocumentType;
import dms.repository.ActivityRepository;
import dms.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentDtoSaveValidator implements DtoValidator<DocumentDto> {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void validate(DocumentDto documentDto) throws Exception {
        List<String> errors = new ArrayList<>();

        if (documentDto.getName() == null || documentDto.getName().isEmpty()) {
            errors.add("Name is required filed");
        }
        if (documentDto.getFile() == null || documentDto.getFile().length == 0) {
            errors.add("File is required filed");
        }

        if (documentDto.getInputActivityId() != null && !documentDto.getInputActivityId().toString().isEmpty() && !activityRepository.findById(documentDto.getInputActivityId()).isPresent()) {
            errors.add("You must choose valid activity");
        }
        if (documentDto.getOutputActivityId() != null && !documentDto.getOutputActivityId().toString().isEmpty() && !activityRepository.findById(documentDto.getOutputActivityId()).isPresent()) {
            errors.add("You must choose valid activity");
        }
        if (documentDto.getOutputActivityId() == null && documentDto.getInputActivityId() == null) {
            errors.add("You must assignee document to at least one activity");
        }

        if (documentDto.getDocumentTypeId() == null || documentTypeRepository.getOne(documentDto.getDocumentTypeId()) == null) {
            errors.add("Document type is required field");
        } else {
            DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId()).get();

            for (DescriptorType descriptorType : documentType.getDescriptorTypes()) {
                boolean exists = false;
                for (DescriptorDto descriptorDto : documentDto.getDescriptorDtoList()) {

                    if (descriptorDto.getTypeId().equals(descriptorType.getId())) {
                        exists = true;
                        if (descriptorType.isMandatory() && (descriptorDto.getValue() == null || descriptorDto.getValue().toString().isEmpty())) {
                            errors.add(descriptorType.getName() + " is reqired filed");
                        } else if (descriptorDto.getValue() != null && !descriptorDto.toString().isEmpty()) {

                            switch (descriptorType.getType()) {
                                case Numeric:
                                    if (!isNumber(descriptorDto.getValue())) {
                                        errors.add(descriptorType.getName() + " must be in number format");
                                    }
                                    break;
                                case Decimal:
                                    if (!isDecimal(descriptorDto.getValue())) {
                                        errors.add(descriptorType.getName() + " must be in decimal format");
                                    }
                                    break;
                                case Boolean:
                                    if (!isBoolean(descriptorDto.getValue())) {
                                        errors.add(descriptorType.getName() + " must have one of two values (true/false)");
                                    }
                                    break;
                                case Date:
                                    if (!isDate(descriptorDto.getValue())) {
                                        errors.add(descriptorType.getName() + " must be in valid date format (example: 01.01.2019.)");
                                    }
                                    break;
                            }
                        }
                    }
                }
                if (!exists) {
                    errors.add("Invalid number of descriptros");
                    break;
                }
            }
        }
        if (errors.size() > 0) {
            throw new DmsException("Error while saving document", errors);
        }

    }

    private boolean isNumber(Object value) {
        try {
            Integer.parseInt(value.toString());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isDecimal(Object value) {
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isBoolean(Object value) {
        return "true".equals(value.toString()) || "false".equals(value.toString());
    }

    private boolean isDate(Object value) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(value.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
