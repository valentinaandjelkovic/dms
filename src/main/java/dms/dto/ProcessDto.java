package dms.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class ProcessDto implements EntityDto {

    private Long id;
    @NotEmpty(message = "Name of process is required filed")
    private String name;
    private CompanyDto companyDto;
    private ProcessDto parentDto;
    private Long parentId;
    private boolean primitive;
    private Long companyId;


    public ProcessDto() {
    }

    public ProcessDto(Long id, String name, boolean primitive) {
        this.id = id;
        this.name = name;
        this.primitive = primitive;
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

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public ProcessDto getParentDto() {
        return parentDto;
    }

    public void setParentDto(ProcessDto parentDto) {
        this.parentDto = parentDto;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessDto)) return false;
        ProcessDto that = (ProcessDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
