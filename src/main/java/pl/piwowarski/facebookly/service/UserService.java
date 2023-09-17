package pl.piwowarski.facebookly.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.CreateUserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.UserMapper;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserDto findUserById(long id){
        User foundUser = userRepository
                .findById(id)
                .orElseThrow(() -> new NoUserWithSuchId("Brak użytkowników o podanym id"));
        return userMapper.unmap(foundUser);
    }

    public CreateUserDto addUser(CreateUserDto createUserDto) {
        User user = userMapper.map(createUserDto);
        User savedUser = userRepository.save(user);
        return userMapper.unmap(savedUser);
    }
}
