package dms.service.impl;

import dms.dto.UserDto;
import dms.dto.formatter.UserDtoFormatter;
import dms.dto.validator.UserDtoSaveValidator;
import dms.exception.ResourceNotFoundException;
import dms.model.User;
import dms.repository.UserRepository;
import dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDtoFormatter formatter;

    @Autowired
    private UserDtoSaveValidator saveValidator;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new ResourceNotFoundException("User not found");
        return user.get();
    }

    @Override
    public User save(UserDto userDto) throws Exception {
        System.out.println("User id " + userDto.getId());
        if (userDto.getId() == null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        saveValidator.validate(userDto);
        return userRepository.save(formatter.formatToEntity(userDto));
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
