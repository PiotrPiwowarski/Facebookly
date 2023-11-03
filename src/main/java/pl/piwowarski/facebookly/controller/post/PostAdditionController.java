package pl.piwowarski.facebookly.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.post.impl.PostAdditionService;

import java.net.URI;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostAdditionController {

    private final PostAdditionService postAdditionService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Dodanie posta.",
            description = "Opcjonalne dane: kontent, ścieżka do obrazka. Wymagane dane: id użytkownika, token. " +
                    "Zwracane dane: brak.")
    @PostMapping
    public ResponseEntity<Void> addPost(@RequestBody AddPostDto postDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(postDto.getToken(), postDto.getUserId(), authorizedRoles);
        PostDto post = postAdditionService.addPost(postDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + post.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
