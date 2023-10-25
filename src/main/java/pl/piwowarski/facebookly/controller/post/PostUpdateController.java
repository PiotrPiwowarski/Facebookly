package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.post.impl.PostUpdateService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostUpdateController {

    private final PostUpdateService postUpdateService;
    private final AuthenticationService authenticationService;

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId,
                                              @RequestBody PostDto postDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(postDto.getToken(), postDto.getUserId(), authorizedRoles);
        PostDto post = postUpdateService.updatePost(postId, postDto);
        return ResponseEntity.ok(post);
    }
}
