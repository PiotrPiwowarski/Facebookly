package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.impl.PostDtoToPostMapper;
import pl.piwowarski.facebookly.service.mapper.impl.PostToPostDtoMapper;
import pl.piwowarski.facebookly.service.post.PostService;

@Service
@RequiredArgsConstructor
public class PostAdditionService implements PostService {

    private final PostRepository postRepository;
    private final PostToPostDtoMapper postToPostDtoMapper;
    private final PostDtoToPostMapper postDtoToPostMapper;

    public PostDto addPost(PostDto postDto) {
        Post post = postDtoToPostMapper.map(postDto);
        Post savedPost = postRepository.save(post);
        return postToPostDtoMapper.map(savedPost);
    }
}
