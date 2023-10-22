package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.PostContentIsNullException;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.map.impl.PostMapper;
import pl.piwowarski.facebookly.service.mapper.reverseMap.impl.PostReverseMapper;

import java.util.Comparator;
import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final PostReverseMapper postReverseMapper;

    public PostDto findPostById(Long postId) {
        Post foundPost = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoPostWithSuchIdException("Brak postów o podanym id"));
        return postMapper.map(foundPost);
    }

    public List<PostDto> findAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::map)
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
                .map(postMapper::map)
                .toList();
    }

    public PostDto savePost(PostDto postDto) {
        Post post = postReverseMapper.map(postDto);
        Post savedPost = postRepository.save(post);
        return postMapper.map(savedPost);
    }

    public void deletePost(Long postId, Long userId, Role role) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE));
        if(!post.getUser().getId().equals(userId) && role == USER){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        postRepository.deleteById(postId);
    }

    public List<PostDto> findAllUserPosts(Long userId, Integer pageNumber, Integer pageSize) {
        return postRepository
                .findAllByUserId(userId)
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postMapper::map)
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
                .map(postMapper::map)
                .toList();
    }

    @Transactional
    public void addLike(Long postId) {
        Post post = findById(postId);
        post.setLikes(post.getLikes() + 1);

    }

    @Transactional
    public void addDislike(Long postId){
        Post post = findById(postId);
        post.setLikes(post.getDislikes() + 1);
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
        return postMapper.map(post);
    }

    public void deleteByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        postRepository.deleteAll(posts);
    }
}
