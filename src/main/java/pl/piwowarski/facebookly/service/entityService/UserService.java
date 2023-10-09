package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.TryingToAddYourselfAsAFriendException;
import pl.piwowarski.facebookly.exception.UserNotLoggedInException;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.LogDataDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.UserMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserMapper userMapper;

    public UserDto findUserById(Long userId){
        User foundUser = findById(userId);
        return userMapper.unmap(foundUser);
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
                .map(userMapper::unmap)
                .toList();
    }

    public List<UserDto> findAllUsers(Integer pageNumber, Integer pageSize){
        return userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(userMapper::unmap)
                .toList();
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.unmap(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findById(id);
        commentService.deleteByUserId(id);
        postService.deleteByUserId(id);
        user.getFriends().forEach(u -> deleteFriend(u.getId(), user.getId()));
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Long userId, UserDto userDto){
        User user = findById(userId);
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
        if(userDto.getLogin() != null){
			user.setLogin(userDto.getLogin());
        }
        if(userDto.getPassword() != null){
			user.setPassword(userDto.getPassword());
        }
        return userMapper.unmap(user);
    }

    public List<UserDto> findUserFriends(Long userId) {
        User user = findById(userId);
        return user.getFriends().stream().map(userMapper::unmap).toList();
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
        User friend = findById(friendId);
        List<User> friends = user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
    }

    public LogDataDto authenticate(CredentialsDto credentialsDto) {
        String email = credentialsDto.getEmail();
        String password = credentialsDto.getPassword();
        return sessionService.login(email, password);
    }

    public void verifySession(LogDataDto logDataDto){
        User user = findById(logDataDto.getUserId());
        if(!user.getLogged()){
            throw new UserNotLoggedInException(UserNotLoggedInException.MESSAGE);
        }
        sessionService.verifySession(logDataDto.getToken());
    }

    @Transactional
    public void logout(LogDataDto logDataDto) {
        User user = findById(logDataDto.getUserId());
        user.setLogged(false);
        String token = logDataDto.getToken();
        sessionService.logout(token);
    }
}
