package dms.dto.formatter;

import java.util.List;

public interface DtoFormatter<Entity, EntityDto> {

    EntityDto formatToDto(Entity entity);

    List<EntityDto> formatToDto(List<Entity> entityList);

    Entity formatToEntity(EntityDto entityDto);

    List<Entity> formatToEntity(List<EntityDto> dtoList);
}
