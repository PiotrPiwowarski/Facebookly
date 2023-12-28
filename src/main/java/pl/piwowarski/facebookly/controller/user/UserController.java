package pl.piwowarski.facebookly.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.dto.user.UpdateUserDto;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Dodawanie użytkownika.",
            description = "Wymagane dane: imię, nazwisko, płeć, email, hasło. Zwracane dane: id użytkownika.")
    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody AddUserDto addUserDto) {
        UserDto userDto = userService.addUser(addUserDto);
        return new ResponseEntity<>(userDto.getId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Dodawanie użytkownika do obserwowanych.",
            description = "Wymagane dane: id użytkownika, id zaobserwowanego. Zwracane dane: brak.")
    @PostMapping("/{userId}/followedUser/{followedUserId}")
    public ResponseEntity<Void> addFollowedUser(@PathVariable long userId,
                                                @PathVariable long followedUserId) {
        userService.checkLoginStatus(userId);
        userService.addFollowedUser(userId, followedUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Pobieranie użytkownika po id.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: użytkownik.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        UserDto userDto = userService.getUserDto(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Pobieranie użytkownika po imieniu.",
            description = "Wymagane dane: imię i nazwisko użytkownika. Zwracane dane: lista użytkowników.")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String firstName,
                                                        @RequestParam String lastName) {
        List<UserDto> users = userService.getUsersByUserName(firstName, lastName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Pobieranie wszystkich użytkowników.",
            description = "Wymagane dane: brak. Zwracane dane: lista użytkowników.")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Pobieranie strony użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony. Zwracane dane: strona użytkowników.")
    @GetMapping("/paged")
    public ResponseEntity<List<UserDto>> getPagedUsers(@RequestParam int pageNumber,
                                                       @RequestParam int pageSize) {
        List<UserDto> pagedUsers = userService.getPagedUsers(pageNumber, pageSize);
        return new ResponseEntity<>(pagedUsers, HttpStatus.OK);
    }

    @Operation(summary = "Pobieranie wszystkich obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: lista użytkowników.")
    @GetMapping("{userId}/followedUsers")
    public ResponseEntity<List<UserDto>> getFollowedUsers(@PathVariable long userId) {
        userService.checkLoginStatus(userId);
        List<UserDto> followedUsers = userService.getFollowedUsers(userId);
        return new ResponseEntity<>(followedUsers, HttpStatus.OK);
    }

    @Operation(summary = "Pobieranie strony obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika, numer strony, rozmiar strony. Zwracane dane: strona użytkowników.")
    @GetMapping("{userId}/followedUsers/paged")
    public ResponseEntity<List<UserDto>> getPagedFollowedUsers(@PathVariable long userId,
                                                               @RequestParam int pageNumber,
                                                               @RequestParam int pageSize) {
        userService.checkLoginStatus(userId);
        List<UserDto> pagedFollowedUsers = userService.getPagedFollowedUsers(userId, pageNumber, pageSize);
        return new ResponseEntity<>(pagedFollowedUsers, HttpStatus.OK);
    }

    @Operation(summary = "Usunięcie siebie samego.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: brak.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userService.checkLoginStatus(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Usunięcie użytkownika ze znajomych.",
            description = "Wymagane dane: id użytkownika, id obserwowanego użytkownika. Zwracane dane: brak.")
    @DeleteMapping("{userId}/followedUsers/{followedUserId}")
    public ResponseEntity<Void> deleteFollowedUser(@PathVariable long userId,
                                                   @PathVariable long followedUserId) {
        userService.checkLoginStatus(userId);
        userService.deleteFollowedUser(userId, followedUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Aktualizacja użytkownika.",
            description = "Wymagane dane: id użytkownika, imię, nazwisko, płeć, email, hasło. Zwracane dane: użytkownik.")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId,
                                              @RequestBody UpdateUserDto updateUserDto) {
        userService.checkLoginStatus(userId);
        UserDto userDto = userService.updateUser(userId, updateUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
