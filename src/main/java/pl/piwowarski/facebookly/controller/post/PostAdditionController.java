package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.reaction.impl.PostReactionServiceService;
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
    private final PostReactionServiceService postReactionService;

    @PostMapping
    public ResponseEntity<Void> addPost(@RequestBody PostDto postDto){
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

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long postId,
                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addLike(postId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addDislike(postId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }
}
