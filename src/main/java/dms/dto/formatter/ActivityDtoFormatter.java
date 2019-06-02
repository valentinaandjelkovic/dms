package dms.dto.formatter;

import dms.dto.ActivityDto;
import dms.model.Activity;
import dms.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class ActivityDtoFormatter implements DtoFormatter<Activity, ActivityDto> {

    @Autowired
    private ProcessDtoFormatter processDtoFormatter;
    @Autowired
    private DocumentDtoFormatter documentDtoFormatter;

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public ActivityDto formatToDto(Activity activity) {
        if (activity == null) {
            return null;
        }
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activity.getId());
        activityDto.setName(activity.getName());
        if (activity.getProcess() != null) {
            activityDto.setProcessDto(processDtoFormatter.formatToDto(activity.getProcess()));
            activityDto.setProcessId(activity.getProcess().getId());
        }
        activityDto.setInputDocumentDtos(documentDtoFormatter.formatToDto(new ArrayList<>(activity.getInputDocuments())));
        activityDto.setOutputDocumentDtos(documentDtoFormatter.formatToDto(new ArrayList<>(activity.getOutputDocuments())));
        return activityDto;
    }

    @Override
    public List<ActivityDto> formatToDto(List<Activity> activities) {
        List<ActivityDto> activityDtoList = new ArrayList<>();
        for (Activity activity : activities) {
            activityDtoList.add(formatToDto(activity));
        }
        return activityDtoList;
    }

    @Override
    public Activity formatToEntity(ActivityDto activityDto) {
        if (activityDto == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(activityDto.getId());
        activity.setName(activityDto.getName());
        activity.setProcess(processRepository.findById(activityDto.getProcessId()).get());
        activity.setInputDocuments(new HashSet<>(documentDtoFormatter.formatToEntity(activityDto.getInputDocumentDtos())));
        activity.setOutputDocuments(new HashSet<>(documentDtoFormatter.formatToEntity(activityDto.getOutputDocumentDtos())));
        return activity;
    }

    @Override
    public List<Activity> formatToEntity(List<ActivityDto> activityDtos) {
        return null;
    }
}
