package dms.dto;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentTypeDto implements EntityDto {
    private Long id;

    @NotEmpty(message = "Name can not be empty")
    private String name;
    private List<DescriptorTypeDto> descriptorTypeDtos;


    public DocumentTypeDto() {
        this.descriptorTypeDtos = new ArrayList<>();
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

    public List<DescriptorTypeDto> getDescriptorTypeDtos() {
        return descriptorTypeDtos;
    }

    public void setDescriptorTypeDtos(List<DescriptorTypeDto> descriptorTypeDtos) {
        this.descriptorTypeDtos = descriptorTypeDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentTypeDto)) return false;
        DocumentTypeDto that = (DocumentTypeDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
