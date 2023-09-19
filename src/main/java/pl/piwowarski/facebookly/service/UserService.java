package pl.piwowarski.facebookly.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.UserMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findUserById(long id){
        User foundUser = userRepository
                .findById(id)
                .orElseThrow(() -> new NoUserWithSuchId("Brak użytkowników o podanym id"));
        return userMapper.unmap(foundUser);
    }

    public List<UserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::unmap)
                .toList();
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.unmap(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
