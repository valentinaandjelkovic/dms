package dms.service.impl;

import dms.dto.ActivityDto;
import dms.dto.formatter.ActivityDtoFormatter;
import dms.dto.validator.ActivityDtoSaveValidator;
import dms.model.Activity;
import dms.repository.ActivityRepository;
import dms.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDtoSaveValidator saveValidator;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityDtoFormatter dtoFormatter;

    @Override
    public Activity save(ActivityDto activityDto) throws Exception {
        saveValidator.validate(activityDto);
        return activityRepository.save(dtoFormatter.formatToEntity(activityDto));
    }

    @Override
    public List<Activity> getByProcessId(Long processId) {
        return activityRepository.findByProcess(processId);
    }

    @Override
    public List<Activity> getByCompany(Long companyId) {
        return activityRepository.findByCompany(companyId);
    }
}
