package dms.service;

import dms.dto.formatter.UserDtoFormatter;
import dms.exception.ResourceNotFoundException;
import dms.model.Company;
import dms.model.Role;
import dms.model.User;
import dms.repository.UserRepository;
import dms.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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

    //2.option
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAllTest() {
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
    public void getByIdTest() throws ResourceNotFoundException {
        when(userRepository.findById(1l)).thenReturn(java.util.Optional.of(new User(1l, "Valentina", "Andjelkovic", "valentina", "valentina123", null, new ArrayList<>())));
        User user = userService.getById(1l);

        assertEquals("Valentina", user.getFirstName());
        assertEquals("Andjelkovic", user.getLastName());
        assertEquals("valentina", user.getUsername());
    }


}
