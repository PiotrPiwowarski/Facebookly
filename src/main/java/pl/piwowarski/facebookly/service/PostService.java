package pl.piwowarski.facebookly.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.model.dto.CreatePostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.exception.NoPostWithSuchId;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.PostMapper;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public CreatePostDto findPostById(long id) {
        Post foundPost = postRepository
                .findById(id)
                .orElseThrow(() -> new NoPostWithSuchId("Brak postów o podanym id"));
        return postMapper.unmap(foundPost);
    }

    public CreatePostDto addPost(CreatePostDto createPostDto) {
        Post post = postMapper.map(createPostDto);
        post.setContent(createPostDto.getContent());
        Post savedPost = postRepository.save(post);
        return postMapper.unmap(savedPost);
    }
}
