package dms.dto.formatter;

import dms.dto.DescriptorDto;
import dms.model.Descriptor;
import dms.repository.DescriptorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescriptorDtoFormatter implements DtoFormatter<Descriptor, DescriptorDto> {

    @Autowired
    private DescriptorTypeDtoFormatter descriptorTypeDtoFormatter;

    @Autowired
    private DescriptorTypeRepository descriptorTypeRepository;

    @Override
    public DescriptorDto formatToDto(Descriptor descriptor) {
        if (descriptor == null) {
            return null;
        }
        return new DescriptorDto(descriptor.getId(), descriptorTypeDtoFormatter.formatToDto(descriptor.getType()), descriptor.getType().getId(), descriptor.getValue());

    }

    @Override
    public List<DescriptorDto> formatToDto(List<Descriptor> descriptors) {
        List<DescriptorDto> descriptorDtos = new ArrayList<>();
        for (Descriptor descriptor : descriptors) {
            descriptorDtos.add(formatToDto(descriptor));
        }
        return descriptorDtos;
    }

    @Override
    public Descriptor formatToEntity(DescriptorDto descriptorDto) {
        if (descriptorDto == null) {
            return null;
        }
        Descriptor descriptor = new Descriptor();
        descriptor.setId(descriptorDto.getId());
        if (descriptorDto.getTypeId() != null) {
            descriptor.setType(descriptorTypeRepository.findById(descriptorDto.getTypeId()).get());
        }
        descriptor.setValue(descriptorDto.getValue());


        return descriptor;
    }

    @Override
    public List<Descriptor> formatToEntity(List<DescriptorDto> descriptorDtos) {
        List<Descriptor> descriptors = new ArrayList<>();
        for (DescriptorDto descriptorDto : descriptorDtos) {
            descriptors.add(formatToEntity(descriptorDto));
        }
        return descriptors;

    }
}
