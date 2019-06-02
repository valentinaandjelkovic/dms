package dms.dto.formatter;

import dms.dto.RoleDto;
import dms.model.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDtoFormatter implements DtoFormatter<Role, RoleDto> {

    @Override
    public RoleDto formatToDto(Role entity) {
        if (entity == null) {
            return null;
        }
        Role role = (Role) entity;
        return new RoleDto(role.getId(), role.getName());
    }

    @Override
    public List<RoleDto> formatToDto(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            roleDtoList.add(formatToDto(role));
        }
        return roleDtoList;
    }

    @Override
    public Role formatToEntity(RoleDto roleDto) {
        return null;
    }

    @Override
    public List<Role> formatToEntity(List<RoleDto> roleDtos) {
        return null;
    }

}
