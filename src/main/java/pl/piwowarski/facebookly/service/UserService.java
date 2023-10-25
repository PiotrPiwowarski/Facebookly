package pl.piwowarski.facebookly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.*;
import pl.piwowarski.facebookly.model.dto.AddUserDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.authenticator.AuthenticationService;
import pl.piwowarski.facebookly.service.mapper.impl.AddUserDtoToUserMapper;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;
import pl.piwowarski.facebookly.service.validator.impl.PasswordValidator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserToUserDtoMapper userToUserDtoMapper;
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final PasswordValidator passwordValidator;

    public UserDto saveUser(AddUserDto addUserDto) {
        passwordValidator.validate(addUserDto.getPassword());
        User user = addUserDtoToUserMapper.map(addUserDto);
        User savedUser = userRepository.save(user);
        return userToUserDtoMapper.map(savedUser);
    }

    @Transactional
    public void deleteUser(Long id, SessionDto sessionDto) {
        User user = findById(id);
        commentService.deleteByUserId(id);
        postService.deleteByUserId(id);
        user.getFriends().forEach(u -> deleteFriend(u.getId(), user.getId()));
        authenticationService.logout(sessionDto);
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
        return userToUserDtoMapper.map(user);
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
}
