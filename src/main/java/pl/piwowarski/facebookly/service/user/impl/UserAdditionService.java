package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.ThisUserAlreadyExistsOnUserFriendsListException;
import pl.piwowarski.facebookly.exception.TryingToAddYourselfAsAFriendException;
import pl.piwowarski.facebookly.exception.UserWithSuchEmailAlreadyExistsException;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.impl.AddUserDtoToUserMapper;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;
import pl.piwowarski.facebookly.service.user.UserService;
import pl.piwowarski.facebookly.service.validator.impl.PasswordValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdditionService implements UserService {

    private final UserRepository userRepository;
    private final UserGetService userGetService;
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final UserToUserDtoMapper userToUserDtoMapper;
    private final PasswordValidator passwordValidator;

    public UserDto addUser(AddUserDto addUserDto) {
        if(userRepository.findByEmail(addUserDto.getEmail()).isPresent()){
            throw new UserWithSuchEmailAlreadyExistsException();
        }
        passwordValidator.validate(addUserDto.getPassword());
        User user = addUserDtoToUserMapper.map(addUserDto);
        User savedUser = userRepository.save(user);
        return userToUserDtoMapper.map(savedUser);
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        if(userId.equals(friendId)){
            throw new TryingToAddYourselfAsAFriendException();
        }
        addToFriend(userId, friendId);
        addToFriend(friendId, userId);
    }

    private void addToFriend(Long userId, Long friendId){
        User user = userGetService.getUserById(userId);
        if(user.getFriends().stream().anyMatch(friend -> friend.getId().equals(friendId))){
            throw new ThisUserAlreadyExistsOnUserFriendsListException();
        }
        User friend = userGetService.getUserById(friendId);
        List<User> friends = user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
    }
}
