package dms.dto.formatter;

import dms.dto.DescriptorTypeDto;
import dms.model.DescriptorType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescriptorTypeDtoFormatter implements DtoFormatter<DescriptorType, DescriptorTypeDto> {

    @Override
    public DescriptorTypeDto formatToDto(DescriptorType entity) {
        if (entity == null) {
            return null;
        }
        return new DescriptorTypeDto(entity.getId(), entity.getName(), entity.getType(), entity.isMandatory());
    }

    @Override
    public List<DescriptorTypeDto> formatToDto(List<DescriptorType> descriptorTypes) {
        List<DescriptorTypeDto> descriptorTypeDtos = new ArrayList<>();
        for (DescriptorType descriptorType : descriptorTypes) {
            descriptorTypeDtos.add(formatToDto(descriptorType));
        }
        return descriptorTypeDtos;
    }

    @Override
    public DescriptorType formatToEntity(DescriptorTypeDto dto) {
        if (dto == null) {
            return null;
        }
        return new DescriptorType(dto.getId(), dto.getName(), dto.getType(), dto.isMandatory());
    }

    @Override
    public List<DescriptorType> formatToEntity(List<DescriptorTypeDto> descriptorTypeDtos) {
        return null;
    }
}
