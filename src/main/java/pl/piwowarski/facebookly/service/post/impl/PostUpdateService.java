package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.PostContentIsNullException;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.service.mapper.impl.PostToPostDtoMapper;
import pl.piwowarski.facebookly.service.post.PostService;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostService {

    private final PostGetService postGetService;
    private final PostToPostDtoMapper postToPostDtoMapper;

    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postGetService.getPostById(postId);
        if(post.getContent() == null){
            throw new PostContentIsNullException();
        }
        post.setContent(postDto.getContent());
        return postToPostDtoMapper.map(post);
    }
}
