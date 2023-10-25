package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.post.PostService;

import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class PostRemovalService implements PostService {

    private final PostRepository postRepository;
    private final PostGetService postGetService;

    public void deletePost(Long postId, Long userId, Role role) {
        Post post = postGetService.getPostById(postId);
        if(!post.getUser().getId().equals(userId) && role == USER){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        postRepository.deleteById(postId);
    }

    public void deletePostByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        postRepository.deleteAll(posts);
    }

    public void deletePostByAdminId(Long ownerId, Role role) {
        if(!role.equals(ADMIN)){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        List<Post> posts = postRepository.findAllByUserId(ownerId);
        postRepository.deleteAll(posts);
    }
}
