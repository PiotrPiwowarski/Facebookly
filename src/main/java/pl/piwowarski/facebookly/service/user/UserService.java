package pl.piwowarski.facebookly.service.user;

import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.dto.user.UpdateUserDto;
import pl.piwowarski.facebookly.model.entity.User;

import java.util.List;

public interface UserService {

    UserDto addUser(AddUserDto addUserDto);
    void addFollowedUser(long userId, long friendId);
    UserDto getUserDto(long userId);
    User getUser(long userId);
    List<UserDto> getUsersByUserName(String firstName, String lastName);
    List<UserDto> getAllUsers();
    List<UserDto> getPagedUsers(int pageNumber, int pageSize);
    List<UserDto> getFollowedUsers(long userId);
    List<UserDto> getPagedFollowedUsers(long userId, int pageNumber, int pageSize);
    void deleteUser(long userId);
    void deleteFollowedUser(long userId, long followedUserId);
    UserDto updateUser(long userId, UpdateUserDto updateUserDto);
}
