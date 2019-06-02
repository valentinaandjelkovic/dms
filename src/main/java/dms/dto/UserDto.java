package dms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto implements EntityDto {

    private Long id;
    @NotEmpty(message = "First name can not be empty")
    @Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    @Size(min = 1, max = 30, message = "Last name must be between 1 and 30 characters")
    private String lastName;

    @NotEmpty(message = "Username can not be empty")
    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    private String username;
    @NotEmpty(message = "Password can not be empty")
    private String password;

    private CompanyDto companyDto;

    private List<RoleDto> roleDtoList;

    @Size(min = 1, message = "You must choose at least one user role")
    private List<Long> roleIds;

    @NotNull(message = "You must choose company")
    private Long companyId;

    public UserDto() {
        this.roleIds = new ArrayList<>();
        this.roleDtoList = new ArrayList<>();
    }

    public UserDto(Long id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roleDtoList = new ArrayList<>();
        this.roleIds = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public List<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(List<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
