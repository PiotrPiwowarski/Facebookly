package pl.piwowarski.facebookly.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.exception.NoPostWithSuchId;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.PostMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDto findPostById(Long id) {
        Post foundPost = postRepository
                .findById(id)
                .orElseThrow(() -> new NoPostWithSuchId("Brak postów o podanym id"));
        return postMapper.unmap(foundPost);
    }

    public List<PostDto> findAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::unmap)
                .toList();
    }

    public PostDto savePost(PostDto postDto) {
        Post post = postMapper.map(postDto);
        Post savedPost = postRepository.save(post);
        return postMapper.unmap(savedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
