package dms.dto.validator;

public interface DtoValidator<EntityDto> {

    void validate(EntityDto dto) throws Exception;

}
