package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.CreatePostDto;
import pl.piwowarski.facebookly.model.dto.CreateUserDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.UserService;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserDto> getUserById(@PathVariable long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CreateUserDto> addUser(@RequestBody CreateUserDto createUserDto){
        CreateUserDto user = userService.addUser(createUserDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + user.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
