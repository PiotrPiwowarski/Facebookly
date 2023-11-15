package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchEmailException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.user.GetUserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.impl.UserToGetUserDtoMapper;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGetService implements UserService {

    private final UserRepository userRepository;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    public GetUserDto getUserDtoById(Long userId){
        User foundUser = getUserById(userId);
        return userToGetUserDtoMapper.map(foundUser);
    }

    public User getUserById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(NoUserWithSuchIdException::new);
    }

    public List<GetUserDto> getUsersByUserName(String firstName, String lastName) {
        return userRepository
                .findAllByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(userToGetUserDtoMapper::map)
                .toList();
    }

    public User getUserByEmail(String email){
        return userRepository
                .findByEmail(email)
                .orElseThrow(NoUserWithSuchEmailException::new);
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userToGetUserDtoMapper::map)
                .toList();
    }

    public List<GetUserDto> getPagedUsers(Integer pageNumber, Integer pageSize){
        return userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(userToGetUserDtoMapper::map)
                .toList();
    }

    public List<GetUserDto> getFollowedUsers(Long userId) {
        return userRepository
                .findFollowedUsersByUserId(userId)
                .stream()
                .map(userToGetUserDtoMapper::map)
                .toList();
    }

    public List<GetUserDto> getPagedFollowedUsers(Long userId, Integer pageNumber, Integer pageSize) {
        return userRepository
                .findPagedFollowedUsersByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(userToGetUserDtoMapper::map)
                .toList();
    }
}
