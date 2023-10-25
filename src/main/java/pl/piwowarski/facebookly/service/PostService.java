package pl.piwowarski.facebookly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.PostContentIsNullException;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.impl.PostDtoToPostMapper;
import pl.piwowarski.facebookly.service.mapper.impl.PostToPostDtoMapper;

import java.util.Comparator;
import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikesAndDislikesService postLikesAndDislikesService;
    private final PostToPostDtoMapper postToPostDtoMapper;
    private final PostDtoToPostMapper postDtoToPostMapper;

    public PostDto findPostById(Long postId) {
        Post foundPost = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoPostWithSuchIdException("Brak postów o podanym id"));
        return postToPostDtoMapper.map(foundPost);
    }

    public List<PostDto> findAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postToPostDtoMapper::map)
                .sorted(Comparator
                        .comparing(PostDto::getCreated)
                        .reversed())
                .toList();
    }

    public List<PostDto> findAllPosts(Integer pageNumber, Integer pageSize) {
        return postRepository
                .findAll(PageRequest
                        .of(pageNumber, pageSize)
                        .withSort(Sort.by("created")
                                .reverse()))
                .stream()
                .map(postToPostDtoMapper::map)
                .toList();
    }

    public PostDto savePost(PostDto postDto) {
        Post post = postDtoToPostMapper.map(postDto);
        Post savedPost = postRepository.save(post);
        return postToPostDtoMapper.map(savedPost);
    }

    public void deletePost(Long postId, Long userId, Role role) {
        Post post = findPost(postId);
        if(!post.getUser().getId().equals(userId) && role == USER){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        postRepository.deleteById(postId);
    }

    private Post findPost(Long id){
        return postRepository
                .findById(id)
                .orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE));
    }

    public List<PostDto> findAllUserPosts(Long userId, Integer pageNumber, Integer pageSize) {
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

    public List<PostDto> findAllUserPosts(Long userId) {
        return postRepository
                .findAllByUserId(userId)
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList();
    }

    public void addLike(Long postId, Long userId) {
        postLikesAndDislikesService.addLike(postId, userId);
    }

    public void addDislike(Long postId, Long userId){
        postLikesAndDislikesService.addDislike(postId, userId);
    }

    private Post findById(Long postId){
        return postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE)
                );
    }

    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = findById(postId);
        if(post.getContent() == null){
            throw new PostContentIsNullException(PostContentIsNullException.MESSAGE);
        }
        post.setContent(postDto.getContent());
        return postToPostDtoMapper.map(post);
    }

    public void deleteByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        postRepository.deleteAll(posts);
    }

    public void deleteByUserId(Long ownerId, Role role) {
        if(!role.equals(ADMIN)){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        List<Post> posts = postRepository.findAllByUserId(ownerId);
        postRepository.deleteAll(posts);
    }

    public List<UserDto> getAllDislikes(Long postId) {
        return postLikesAndDislikesService.getAllLikes(postId);
    }

    public List<UserDto> getAllLikes(Long postId) {
        return postLikesAndDislikesService.getAllDislikes(postId);
    }
}
