package dms.dto;

import javax.validation.constraints.NotEmpty;
import java.util.*;

public class ActivityDto implements EntityDto {

    private Long id;
    @NotEmpty(message = "Name of activity is required filed")
    private String name;
    private ProcessDto processDto;
    private Long processId;
    private List<DocumentDto> inputDocumentDtos;
    private List<DocumentDto> outputDocumentDtos;

    public ActivityDto() {
        inputDocumentDtos = new ArrayList<>();
        outputDocumentDtos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcessDto getProcessDto() {
        return processDto;
    }

    public void setProcessDto(ProcessDto processDto) {
        this.processDto = processDto;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public List<DocumentDto> getInputDocumentDtos() {
        return inputDocumentDtos;
    }

    public void setInputDocumentDtos(List<DocumentDto> inputDocumentDtos) {
        this.inputDocumentDtos = inputDocumentDtos;
    }

    public List<DocumentDto> getOutputDocumentDtos() {
        return outputDocumentDtos;
    }

    public void setOutputDocumentDtos(List<DocumentDto> outputDocumentDtos) {
        this.outputDocumentDtos = outputDocumentDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityDto)) return false;
        ActivityDto that = (ActivityDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
