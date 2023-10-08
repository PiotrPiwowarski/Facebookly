package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.service.entityService.UserService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public String authenticate(){
        return "";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDto>> getAllUsers(@PathVariable Integer pageNumber,
                                                     @PathVariable Integer pageSize){
        return ResponseEntity.ok(userService.findAllUsers(pageNumber, pageSize));
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findUserFriends(userId));
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

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long userId, @PathVariable Long friendId){
        userService.addFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        UserDto user = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId){
        userService.deleteFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }
}
