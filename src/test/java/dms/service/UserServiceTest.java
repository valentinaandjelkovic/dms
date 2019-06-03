package dms.service;

import dms.dto.CompanyDto;
import dms.dto.RoleDto;
import dms.dto.UserDto;
import dms.dto.formatter.UserDtoFormatter;
import dms.dto.validator.UserDtoSaveValidator;
import dms.exception.ResourceNotFoundException;
import dms.model.Company;
import dms.model.Role;
import dms.model.User;
import dms.repository.UserRepository;
import dms.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

//1.option
//@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDtoFormatter dtoFormatter;

    @Mock
    private UserDtoSaveValidator saveValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    //2.option
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAll() {
        List<User> users = new ArrayList<>();
        Company company = new Company(1l, "Test company", "123456789");
        Role role = new Role(1l, "ROLE_ADMIN");
        Role role2 = new Role(2l, "ROLE_USER_MANAGER");
        User user = new User(1l, "Valentina", "Andjelkovic", "valentina", "valentina123", company, new ArrayList<>());
        User user2 = new User(1l, "Daca", "Andjelkovic", "daca", "daca123", company, new ArrayList<>());

        users.add(user);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getAll();
        System.out.println("User size " + userList.size());
        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetByIdWhenCompanyExists() throws ResourceNotFoundException {
        when(userRepository.findById(1l)).thenReturn(java.util.Optional.of(new User(1l, "Valentina", "Andjelkovic", "valentina", "valentina123", null, new ArrayList<>())));
        User user = userService.getById(1l);

        assertEquals("Valentina", user.getFirstName());
        assertEquals("Andjelkovic", user.getLastName());
        assertEquals("valentina", user.getUsername());

        verify(userRepository, times(1)).findById(1l);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdWhenCompanyDoesNotExists() throws ResourceNotFoundException {
        when(userRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        userService.getById(1l);
    }

    @Test
    public void testSave() throws Exception {
        CompanyDto company = new CompanyDto(1l, "Test", "123456789");
        UserDto userDto = new UserDto(3l, "Valentina", "Andjelkovic", "valentina", "valentina", company, null);
        doNothing().when(saveValidator).validate(userDto);


        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("$2y$12$2XJj1cCh5XtYdCo5nCtgMO44rTmK3x68130xChwVBEtDTr32GImNC");
        User user = new User(3l, "Valentina", "Andjelkovic", "valentina", "$2y$12$2XJj1cCh5XtYdCo5nCtgMO44rTmK3x68130xChwVBEtDTr32GImNC", new Company(1l, "Test", "123456789"), null);
        when(dtoFormatter.formatToEntity(userDto)).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);
        userService.save(userDto);

        verify(saveValidator).validate(userDto);
        verify(dtoFormatter).formatToEntity(userDto);
        verify(userRepository).save(user);
    }

    @Test
    public void testDeleteByIdWhenCompanyExists() throws ResourceNotFoundException {

        Company company = new Company(1l, "Test", "123456789");
        User user = new User(1l, "Valetnina", "Andjelkovic", "valentina", "$2y$12$2XJj1cCh5XtYdCo5nCtgMO44rTmK3x68130xChwVBEtDTr32GImNC", company, null);
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        doNothing().when(userRepository).deleteById(1l);
        userService.deleteById(1l);

        verify(userRepository, times(1)).findById(1l);
        verify(userRepository, times(1)).deleteById(1l);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteByIdWhenCompanyDoesNotExist() throws ResourceNotFoundException {
        when(userRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        userService.deleteById(1l);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("User not found");

        verify(userRepository).findById(1l);
    }

}
