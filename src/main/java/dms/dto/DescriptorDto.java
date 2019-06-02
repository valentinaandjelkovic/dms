package dms.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Objects;

public class DescriptorDto implements EntityDto {

    private Long id;
    private DescriptorTypeDto type;
    private Long typeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Object value;


    public DescriptorDto() {
    }

    public DescriptorDto(Long id, DescriptorTypeDto type, Long typeId, Object value) {
        this.id = id;
        this.type = type;
        this.typeId = typeId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public DescriptorTypeDto getType() {
        return type;
    }

    public void setType(DescriptorTypeDto type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescriptorDto)) return false;
        DescriptorDto that = (DescriptorDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
