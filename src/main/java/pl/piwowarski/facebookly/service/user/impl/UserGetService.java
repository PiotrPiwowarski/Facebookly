package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchEmailException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGetService implements UserService {

    private final UserRepository userRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;

    public UserDto getUserDtoById(Long userId){
        User foundUser = getUserById(userId);
        return userToUserDtoMapper.map(foundUser);
    }

    public User getUserById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(NoUserWithSuchIdException::new);
    }

    public List<UserDto> getUsersByUserName(String firstName, String lastName) {
        return userRepository
                .findAllByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(userToUserDtoMapper::map)
                .toList();
    }

    public User getUserByEmail(String email){
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NoUserWithSuchEmailException("Niema użytkownika o podanym id"));
                /*.orElseThrow(NoUserWithSuchEmailException::new);*/
    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userToUserDtoMapper::map)
                .toList();
    }

    public List<UserDto> getPagedUsers(Integer pageNumber, Integer pageSize){
        return userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(userToUserDtoMapper::map)
                .toList();
    }

    public List<UserDto> getUserFriends(Long userId) {
        User user = getUserById(userId);
        return user
                .getFriends()
                .stream()
                .map(userToUserDtoMapper::map)
                .toList();
    }

    public List<UserDto> getPagedUserFriends(Long userId, Integer pageNumber, Integer pageSize) {
        return userRepository
                .findFriendsById(userId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(userToUserDtoMapper::map)
                .toList();
    }
}
