package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.*;
import pl.piwowarski.facebookly.mapper.UserMapper;
import pl.piwowarski.facebookly.model.dto.authentication.LoginDataDto;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.dto.user.UpdateUserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.user.UserService;
import pl.piwowarski.facebookly.validator.PasswordValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkLoginStatus(long id) {
        User user = getUser(id);
        if(!user.isLogged()) {
            throw new UserNotLoggedInException();
        }
    }

    @Override
    public long login(LoginDataDto loginDataDto) {
        User user = getUserByEmail(loginDataDto.getEmail());
        if(!passwordEncoder.matches(loginDataDto.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        user.setLogged(true);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void logout(long id) {
        User user = getUser(id);
        user.setLogged(false);
        userRepository.save(user);
    }

    @Override
    public UserDto addUser(AddUserDto addUserDto) {
		if(checkEmailUnavailability(addUserDto.getEmail())) {
            throw new UserWithSuchEmailAlreadyExistsException();
        }
        passwordValidator.validate(addUserDto.getPassword());
        User user = UserMapper.toEntity(addUserDto, passwordEncoder);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void addFollowedUser(long userId, long friendId) {
        if(userId == friendId) {
            throw new TryToFollowYourselfException();
        }
		User user = getUser(userId);
        List<User> followedUsers = userRepository.findFollowedUsersByUser(user);
        User userToFollow = getUser(friendId);
        if(followedUsers.contains(userToFollow)) {
            throw new FollowedUserException();
        }
        followedUsers.add(userToFollow);
        user.setFollowedUsers(followedUsers);
        userRepository.save(user);
    }

    @Override
    public UserDto getUserDto(long userId) {
        User user = getUser(userId);
        return UserMapper.toDto(user);
    }

    @Override
    public User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NoUserWithSuchIdException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(NoUserWithSuchEmailException::new);
    }

    @Override
    public List<UserDto> getUsersByUserName(String firstName, String lastName) {
        return userRepository.findAllByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getPagedUsers(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getFollowedUsers(long userId) {
        User user = getUser(userId);
        return userRepository.findFollowedUsersByUser(user)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getPagedFollowedUsers(long userId, int pageNumber, int pageSize) {
        User user = getUser(userId);
        return userRepository.findPagedFollowedUsersByUser(user, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void deleteUser(long userId) {
		userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteFollowedUser(long userId, long followedUserId) {
        User user = getUser(userId);
        List<User> followedUsers = userRepository.findFollowedUsersByUser(user);
        User followedUser = getUser(followedUserId);
        followedUsers.remove(followedUser);
        user.setFollowedUsers(followedUsers);
        userRepository.save(user);
    }

    @Override
    public UserDto updateUser(long userId, UpdateUserDto updateUserDto) {
        User user = getUser(userId);
        if(!user.getEmail().equals(updateUserDto.getEmail()) && checkEmailUnavailability(updateUserDto.getEmail())) {
            throw new UserWithSuchEmailAlreadyExistsException();
        }
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setGender(updateUserDto.getGender());
        user.setEmail(updateUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }

    private boolean checkEmailUnavailability(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }

}
