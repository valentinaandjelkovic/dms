package dms.dto.formatter;

import dms.dto.ProcessDto;
import dms.model.Company;
import dms.model.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDtoFormatter implements DtoFormatter<Process, ProcessDto> {

    @Autowired
    private CompanyDtoFormatter companyDtoFormatter;


    @Override
    public ProcessDto formatToDto(Process process) {
        if (process == null)
            return null;
        ProcessDto dto = new ProcessDto(process.getId(), process.getName(), process.isPrimitive());
        if (process.getCompany() != null) {
            dto.setCompanyId(process.getCompany().getId());
            dto.setCompanyDto(companyDtoFormatter.formatToDto(process.getCompany()));
        }
        if (process.getParent() != null) {
            dto.setParentId(process.getParent().getId());
            dto.setParentDto(this.formatToDto(process.getParent()));
        }
        return dto;
    }

    @Override
    public List<ProcessDto> formatToDto(List<Process> processes) {
        List<ProcessDto> processDtoList = new ArrayList<>();
        for (Process process : processes) {
            processDtoList.add(formatToDto(process));
        }
        return processDtoList;
    }

    @Override
    public Process formatToEntity(ProcessDto processDto) {
        if (processDto == null)
            return null;
        Process process = new Process();
        process.setId(processDto.getId());
        process.setName(processDto.getName());
        process.setPrimitive(processDto.isPrimitive());
        if (processDto.getParentId() != null) {
            process.setParent(new Process(processDto.getParentId()));
        }
        if (processDto.getCompanyId() != null) {
            process.setCompany(new Company(processDto.getCompanyId()));
        }

        return process;
    }

    @Override
    public List<Process> formatToEntity(List<ProcessDto> processDtos) {
        return null;
    }
}
