package dms.dto.validator;

import dms.dto.ProcessDto;
import dms.exception.DmsException;
import dms.model.Activity;
import dms.model.Process;
import dms.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDtoDeleteValidator implements DtoValidator<ProcessDto> {

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public void validate(ProcessDto processDto) throws Exception {
        List<String> errors = new ArrayList<>();

        List<Process> processes = processRepository.findByParent(processDto.getId());
        for (Process process : processes) {
            System.out.println("Podproces: " + process.getName());
            for (Activity activity : process.getActivityList()) {
                if (activity.getInputDocuments().size() > 0 || activity.getOutputDocuments().size() > 0) {
                    errors.add("You can not delete process, there are documents belongs to it!");
                    break;
                }
            }
        }
        if (errors.size() > 0) {
            throw new DmsException("Error while deleting process", errors);
        }
    }
}
