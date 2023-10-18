package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.service.entityService.UserService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/authentication")
    public ResponseEntity<SessionDto> authenticate(@RequestBody CredentialsDto credentialsDto){
        return ResponseEntity.ok(userService.authenticate(credentialsDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        userService.logout(sessionDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDto>> getAllUsers(@PathVariable Integer pageNumber,
                                                     @PathVariable Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(userService.findAllUsers(pageNumber, pageSize));
    }

    @GetMapping("/friends/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Integer pageNumber,
                                                        @PathVariable Integer pageSize,
                                                        @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(userService.findUserFriends(sessionDto.getUserId(), pageNumber, pageSize));
    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserDto>> getUserFriends(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(userService.findUserFriends(sessionDto.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto){
        UserDto user = userService.saveUser(userDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + user.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@RequestBody SessionDto sessionDto, @PathVariable Long friendId){
        userService.verifyUserSession(sessionDto);
        userService.addFriend(sessionDto.getUserId(), friendId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        userService.verifyUserSession(userDto.getToken(), userDto.getId());
        UserDto user = userService.updateUser(userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        userService.deleteUser(sessionDto.getUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        userService.deleteFriend(sessionDto.getUserId(), friendId);
        return ResponseEntity.noContent().build();
    }
}
