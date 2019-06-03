package dms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CompanyDto implements EntityDto {
    private Long id;

    @NotEmpty(message = "Company name can not be empty")
    @Size(min = 2, max = 30, message = "Company name must be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Company number can not be empty")
    @Size(min = 9, max = 9, message = "Company number must have 9 characters")
    private String companyNumber;

    public CompanyDto() {
    }

    public CompanyDto(Long id) {
        this.id = id;

    }


    public CompanyDto(Long id, String name, String companyNumber) {
        this.id = id;
        this.name = name;
        this.companyNumber = companyNumber;
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

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDto)) return false;
        CompanyDto that = (CompanyDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
