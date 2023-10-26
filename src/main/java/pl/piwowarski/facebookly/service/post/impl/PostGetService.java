package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.impl.PostToPostDtoMapper;
import pl.piwowarski.facebookly.service.post.PostService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostGetService implements PostService {

    private final PostRepository postRepository;
    private final PostToPostDtoMapper postToPostDtoMapper;

    public PostDto getPostDtoById(Long postId) {
        Post foundPost = postRepository
                .findById(postId)
                .orElseThrow(NoPostWithSuchIdException::new);
        return postToPostDtoMapper.map(foundPost);
    }

    public Post getPostById(Long postId){
        return postRepository
                .findById(postId)
                .orElseThrow(NoPostWithSuchIdException::new);
    }

    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postToPostDtoMapper::map)
                .sorted(Comparator
                        .comparing(PostDto::getCreated)
                        .reversed())
                .toList();
    }

    public List<PostDto> getPagedPosts(Integer pageNumber, Integer pageSize) {
        return postRepository
                .findAll(PageRequest
                        .of(pageNumber, pageSize)
                        .withSort(Sort.by("created")
                                .reverse()))
                .stream()
                .map(postToPostDtoMapper::map)
                .toList();
    }

    public List<PostDto> getPagedUserPosts(Long userId, Integer pageNumber, Integer pageSize) {
        return postRepository
                .findAllByUserId(userId)
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList()
                .subList(pageNumber, pageSize + pageNumber);
    }

    public List<PostDto> getAllUserPosts(Long userId) {
        return postRepository
                .findAllByUserId(userId)
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList();
    }
}
