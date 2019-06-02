package dms.dto.validator;

import dms.dto.ActivityDto;
import dms.exception.DmsException;
import dms.model.Process;
import dms.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityDtoSaveValidator implements DtoValidator<ActivityDto> {

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public void validate(ActivityDto activityDto) throws Exception {
        List<String> errors = new ArrayList<>();

        if (activityDto.getName() == null || activityDto.getName().trim().isEmpty()) {
            errors.add("Name of activity is required filed");
        }
        if (activityDto.getProcessId() == null || activityDto.getProcessId().toString().isEmpty()) {
            errors.add("You must choose process");
        } else {
            Process process = processRepository.findById(activityDto.getProcessId()).get();
            if (process == null) {
                errors.add("You must choose valid process");
            } else if (!process.isPrimitive()) {
                errors.add("You must choose primitive process as parent to activity");
            }
        }
        if (errors.size() > 0) {
            throw new DmsException("Error while saving activity", errors);
        }
    }
}
