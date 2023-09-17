package pl.piwowarski.facebookly.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.model.dto.CreatePostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.UserRepository;

@Service
@AllArgsConstructor
public class PostMapper implements Mapper<Post, CreatePostDto>{

    private UserRepository userRepository;

    @Override
    public Post map(CreatePostDto createPostDto) {
        Post post = new Post();
        post.setContent(createPostDto.getContent());
        post.setUser(userRepository
                .findById(createPostDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchId("Brak użytkowników o podanym id")));
        return post;
    }

    @Override
    public CreatePostDto unmap(Post post) {
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setId(post.getId());
        createPostDto.setContent(post.getContent());
        createPostDto.setUserId(post.getUser().getId());
        return createPostDto;
    }
}
