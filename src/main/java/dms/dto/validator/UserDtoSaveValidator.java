package dms.dto.validator;

import dms.dto.UserDto;
import dms.exception.DmsException;
import dms.model.User;
import dms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoSaveValidator implements DtoValidator<UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(UserDto userDto) throws Exception {
        List<String> errors = new ArrayList<>();

        if (!isUniqueName(userDto)) {
            errors.add("Username is already in use, try another one");
        }
        if (errors.size() > 0) {
            throw new DmsException("Error while saving user", errors);
        }
    }

    private boolean isUniqueName(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            return true;
        } else if (userDto.getId() != null && userDto.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }
}
