package pl.piwowarski.facebookly.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UpdateUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.user.impl.UserServiceImpl;
import pl.piwowarski.facebookly.validator.PasswordValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.piwowarski.facebookly.model.enums.Gender.MALE;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordValidator passwordValidator;
    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private AddUserDto addUserDto;
    private UserDto userDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    public void init() {
        addUserDto = AddUserDto.builder()
                .firstName("Jan")
                .lastName("Nowakowski")
                .email("jan-nowakowski@gmail.com")
                .gender(MALE)
                .password("nowakowski123")
                .build();

        user1 = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .email("user1@gmail.com")
                .password("abcde1")
                .gender(MALE)
                .role(USER)
                .build();

        user2 = User.builder()
                .id(2L)
                .firstName("John")
                .lastName("Johnowski")
                .role(USER)
                .gender(MALE)
                .email("john@gmail.com")
                .password("asdfasdf")
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .email("user1@gmail.com")
                .gender(MALE)
                .build();

        updateUserDto = UpdateUserDto.builder()
                .firstName("Klaudiusz")
                .lastName("Nowakowski")
                .email("klaudiusz-nowakowski@gmail.com")
                .gender(MALE)
                .password("nowakowski123")
                .build();
    }

    @Test
    public void itShouldAddUser() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);

        UserDto userDto = userService.addUser(addUserDto);

        assertThat(userDto).isNotNull();
    }

    @Test
    public void itShouldAddFollowedUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
		when(userRepository.findFollowedUsersByUser(user1)).thenReturn(new ArrayList<>());

        userService.addFollowedUser(user1.getId(), user2.getId());

        assertThat(user1.getFollowedUsers()).isNotNull();
        assertThat(user1.getFollowedUsers().get(0)).isEqualTo(user2);
    }

    @Test
    public void itShouldGetUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        UserDto userDto = userService.getUserDto(user1.getId());

        assertThat(userDto).isNotNull();
    }

    @Test
    public void itShouldGetUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User user = userService.getUser(user1.getId());

        assertThat(user).isNotNull();
    }

    @Test
    public void itShouldGetUsersByUserName() {
        when(userRepository
                .findAllByFirstNameAndLastName(user1.getFirstName(), user1.getLastName())).thenReturn(List.of(user1));

        List<UserDto> usersByUserName = userService.getUsersByUserName(user1.getFirstName(), user1.getLastName());

        assertThat(usersByUserName).isNotNull();
        assertThat(usersByUserName.get(0)).isEqualTo(userDto);
    }

    @Test
    public void itShouldGetAllUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> allUsers = userService.getAllUsers();

        assertThat(allUsers.size()).isEqualTo(2);
    }

    @Test
    public void itShouldGetPagedUsers() {
        when(userRepository.findAll(PageRequest.of(0, 2)))
                .thenReturn(new PageImpl<>(List.of(user1, user2)));

        List<UserDto> pagedUsers = userService.getPagedUsers(0, 2);

        assertThat(pagedUsers).isNotNull();
        assertThat(pagedUsers.size()).isEqualTo(2);
    }

    @Test
    public void itShouldGetFollowedUsers() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.findFollowedUsersByUser(user2)).thenReturn(List.of(user1));

        List<UserDto> followedUsers = userService.getFollowedUsers(2L);

        assertThat(followedUsers).isNotNull();
        assertThat(followedUsers.get(0)).isEqualTo(userDto);
    }

    @Test
    public void itShouldGetPagedFollowedUsers() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.findPagedFollowedUsersByUser(user2, PageRequest.of(0, 1)))
                .thenReturn(List.of(user1));

        List<UserDto> followedUsers = userService.getPagedFollowedUsers(2L, 0, 1);

        assertThat(followedUsers).isNotNull();
        assertThat(followedUsers.get(0)).isEqualTo(userDto);
    }

    @Test
    public void itShouldDeleteUser() {
		userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void itShouldDeleteFollowedUser() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.findFollowedUsersByUser(user2)).thenReturn(new ArrayList<>(List.of(user1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.deleteFollowedUser(user2.getId(), user1.getId());

        verify(userRepository, times(1)).save(user2);
    }

    @Test
    public void itShouldUpdateUser() {
        UserDto userDto1 = UserDto.builder()
                .firstName("Klaudiusz")
                .lastName("Nowakowski")
                .gender(MALE)
                .email("klaudiusz-nowakowski@gmail.com")
                .id(1L)
                .build();
        User user = User.builder()
                .id(1L)
                .firstName("Klaudiusz")
                .lastName("Nowakowski")
                .role(USER)
                .password("abcde1")
                .email("klaudiusz-nowakowski@gmail.com")
                .gender(MALE)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user);

        UserDto updatedUser = userService.updateUser(1L, updateUserDto);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser).isEqualTo(userDto1);
    }
}