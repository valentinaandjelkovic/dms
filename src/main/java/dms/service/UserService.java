package dms.service;

import dms.dto.UserDto;
import dms.exception.ResourceNotFoundException;
import dms.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAll();

    public User getById(Long id) throws ResourceNotFoundException;

    public User save(UserDto userDto) throws Exception;

    public boolean deleteById(Long id) throws ResourceNotFoundException;

    public User getByUsername(String username);
}
