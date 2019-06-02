package dms.controller;

import dms.datatables.ActivityDataTablesRepository;
import dms.dto.ActivityDto;
import dms.dto.formatter.ActivityDtoFormatter;
import dms.model.Activity;
import dms.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("activity")
@Controller
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityDtoFormatter dtoFormatter;

    @Autowired
    private ActivityDataTablesRepository activityDataTablesRepository;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(@Valid @RequestBody ActivityDto activityDto) throws Exception {
        Activity activity = activityService.save(activityDto);
        if (activity != null) {
            return new ResponseEntity(dtoFormatter.formatToDto(activity), HttpStatus.OK);
        }
        return new ResponseEntity("DmsException, activity was not added, try again!", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity fetch(@PathVariable("id") Long processId) throws Exception {
        return new ResponseEntity(dtoFormatter.formatToDto(activityService.getByProcessId(processId)), HttpStatus.OK);
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    @ResponseBody
    public DataTablesOutput<ActivityDto> search(@Valid @RequestBody DataTablesInput input) {
        DataTablesOutput dataTablesOutput = activityDataTablesRepository.findAll(input, getAdditionalSpecification(input));
        dataTablesOutput.setData(dtoFormatter.formatToDto(dataTablesOutput.getData()));
        return dataTablesOutput;
    }

    private Specification getAdditionalSpecification(DataTablesInput input) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            Column process = input.getColumns().get(1);

            if (process.getSearch().getValue() != null && !process.getSearch().getValue().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("process").get("id"), Long.parseLong(process.getSearch().getValue())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }
}
