package dms.dto.formatter;

import dms.dto.RoleDto;
import dms.dto.UserDto;
import dms.model.Company;
import dms.model.Role;
import dms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoFormatter implements DtoFormatter<User, UserDto> {

    @Autowired
    private CompanyDtoFormatter companyDtoFormatter;

    @Autowired
    private RoleDtoFormatter roleDtoFormatter;

    @Override
    public UserDto formatToDto(User user) {
        if (user == null)
            return null;
        UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());

        if (user.getCompany() != null) {
            userDto.setCompanyDto(companyDtoFormatter.formatToDto(user.getCompany()));
            userDto.setCompanyId(user.getCompany().getId());
        }
        for (Role role : user.getRoles()) {
            userDto.getRoleDtoList().add((RoleDto) roleDtoFormatter.formatToDto(role));
            userDto.getRoleIds().add(role.getId());
        }
        return userDto;
    }

    @Override
    public List<UserDto> formatToDto(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(formatToDto(user));
        }
        return userDtoList;
    }

    @Override
    public User formatToEntity(UserDto userDto) {
        if (userDto == null)
            return null;
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setCompany(new Company(userDto.getCompanyId()));
        for (Long roleId : userDto.getRoleIds()) {
            user.getRoles().add(new Role(roleId));
        }
        return user;
    }

    @Override
    public List<User> formatToEntity(List<UserDto> userDtos) {
        return null;
    }
}
