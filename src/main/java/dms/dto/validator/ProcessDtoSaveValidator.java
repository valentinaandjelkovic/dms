package dms.dto.validator;

import dms.dto.ProcessDto;
import dms.exception.DmsException;
import dms.model.Process;
import dms.repository.CompanyRepository;
import dms.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDtoSaveValidator implements DtoValidator<ProcessDto> {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void validate(ProcessDto processDto) throws Exception {
        List<String> errors = new ArrayList<>();

        if (processDto.getName() == null || processDto.getName().trim().isEmpty()) {
            errors.add("Name of process is required filed");
        }
        if (processDto.getParentId() != null && !processDto.getParentId().equals(-1)) {
            Process parent = processRepository.findById(processDto.getParentId()).get();
            if (parent == null) {
                errors.add("You must choose valid process");
            } else if (parent.isPrimitive()) {
                errors.add("You can not choose primitive process as parent");
            }
        }
        if (processDto.getCompanyId() == null || companyRepository.findById(processDto.getCompanyId()).get() == null) {
            errors.add("You must choose valid company");
        }

        if (errors.size() > 0) {
            throw new DmsException("Error while saving process", errors);
        }
    }
}
