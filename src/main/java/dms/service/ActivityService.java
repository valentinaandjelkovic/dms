package dms.service;

import dms.dto.ActivityDto;
import dms.model.Activity;

import java.util.List;

public interface ActivityService {
    Activity save(ActivityDto activityDto) throws Exception;

    List<Activity> getByProcessId(Long processId);

    List<Activity> getByCompany(Long companyId);
}
