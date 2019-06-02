package dms.dto;

import dms.model.DescriptorClass;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class DescriptorTypeDto implements EntityDto {

    private Long id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotEmpty(message = "You must define type of descriptor")
    private DescriptorClass type;

    private boolean mandatory;


    public DescriptorTypeDto() {
    }

    public DescriptorTypeDto(Long id, String name, DescriptorClass type, boolean mandatory) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
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

    public DescriptorClass getType() {
        return type;
    }

    public void setType(DescriptorClass type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescriptorTypeDto)) return false;
        DescriptorTypeDto that = (DescriptorTypeDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
