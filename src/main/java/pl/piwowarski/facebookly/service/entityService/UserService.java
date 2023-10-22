package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.*;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.credentials.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.session.SessionDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.map.impl.UserMapper;
import pl.piwowarski.facebookly.service.mapper.reverseMap.impl.AddUserReverseMapper;
import pl.piwowarski.facebookly.service.validator.impl.PasswordValidator;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserMapper userMapper;
    private final AddUserReverseMapper addUserReverseMapper;
    private final PasswordValidator passwordValidator;



    public UserDto findUserById(Long userId){
        User foundUser = findById(userId);
        return userMapper.map(foundUser);
    }

    private User findById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE));
    }

    public List<UserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    public List<UserDto> findAllUsers(Integer pageNumber, Integer pageSize){
        return userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(userMapper::map)
                .toList();
    }

    public UserDto saveUser(AddUserDto addUserDto) {
        passwordValidator.validate(addUserDto.getPassword());
        User user = addUserReverseMapper.map(addUserDto);
        User savedUser = userRepository.save(user);
        return userMapper.map(savedUser);
    }

    @Transactional
    public void deleteUser(Long id, String token) {
        User user = findById(id);
        commentService.deleteByUserId(id);
        postService.deleteByUserId(id);
        user.getFriends().forEach(u -> deleteFriend(u.getId(), user.getId()));
        sessionService.logout(token);
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto){
        User user = findById(userDto.getId());
        if(userDto.getFirstName() != null){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null){
			user.setLastName(userDto.getLastName());
        }
        if(userDto.getGender() != null){
			user.setGender(userDto.getGender());
        }
        if(userDto.getEmail() != null){
			user.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword() != null){
			user.setPassword(userDto.getPassword());
        }
        return userMapper.map(user);
    }

    public List<UserDto> findUserFriends(Long userId, Integer pageNumber, Integer pageSize) {
        return userRepository.findFriendsById(userId, PageRequest.of(pageNumber, pageSize)).stream().map(userMapper::map).toList();
    }

    public List<UserDto> findUserFriends(Long userId) {
        User user = findById(userId);
        return user
                .getFriends()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        deleteFromFriends(userId, friendId);
        deleteFromFriends(friendId, userId);
    }

    private void deleteFromFriends(Long userId, Long friendId){
        User user = findById(userId);
        List<User> userFriends = user.getFriends().stream().filter(friend -> !friend.getId().equals(friendId)).toList();
        user.setFriends(userFriends);
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        if(userId.equals(friendId)){
            throw new TryingToAddYourselfAsAFriendException(TryingToAddYourselfAsAFriendException.MESSAGE);
        }
        addToFriend(userId, friendId);
        addToFriend(friendId, userId);
    }

    private void addToFriend(Long userId, Long friendId){
        User user = findById(userId);
        if(user.getFriends().stream().anyMatch(friend -> friend.getId().equals(friendId))){
            throw new ThisUserAlreadyExistOnUserFriendsListException(ThisUserAlreadyExistOnUserFriendsListException.MESSAGE);
        }
        User friend = findById(friendId);
        List<User> friends = user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
    }

    public SessionDto authenticate(CredentialsDto credentialsDto) {
        return sessionService.authenticate(credentialsDto);
    }

    @Transactional
    public void logout(SessionDto sessionDto) {
        User user = findById(sessionDto.getUserId());
        user.setLogged(false);
        String token = sessionDto.getToken();
        sessionService.logout(token);
    }

    public void authorizeAndAuthenticate(SessionDto sessionDto, Set<Role> authorizedRoles){
        User user = findById(sessionDto.getUserId());
        if(!user.getLogged()) {
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        if(!sessionDto.getRole().equals(user.getRole())){
            throw new RolesConflictException(RolesConflictException.MESSAGE);
        }
        sessionService.verifyRole(user.getRole(), authorizedRoles);
        sessionService.verifySession(sessionDto.getToken(), sessionDto.getUserId());
    }

    public void authorizeAndAuthenticate(String token, Long userId, Set<Role> authorizedRoles){
        User user = findById(userId);
        if(!user.getLogged()) {
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        sessionService.verifyRole(user.getRole(), authorizedRoles);
        sessionService.verifySession(token, userId);
    }
}
