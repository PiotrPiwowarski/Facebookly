package pl.piwowarski.facebookly.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.UserRepository;

@Service
@AllArgsConstructor
public class PostMapper implements Mapper<Post, PostDto>{

    private UserRepository userRepository;

    @Override
    public Post map(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreated(postDto.getCreated());
        post.setUser(userRepository
                .findById(postDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchId(NoUserWithSuchId.MESSAGE)));
        post.setLikes(0L);
        post.setDislikes(0L);
        return post;
    }

    @Override
    public PostDto unmap(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setCreated(post.getCreated());
        postDto.setUserId(post.getUser().getId());
        postDto.setLikes(post.getLikes());
        postDto.setDislikes(post.getDislikes());
        return postDto;
    }
}
