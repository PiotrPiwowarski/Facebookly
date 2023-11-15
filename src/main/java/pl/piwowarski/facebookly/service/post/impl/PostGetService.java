package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.dto.user.GetUserDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.impl.PostToPostDtoMapper;
import pl.piwowarski.facebookly.service.post.PostService;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostGetService implements PostService {

    private final PostRepository postRepository;
    private final PostToPostDtoMapper postToPostDtoMapper;
    private final UserGetService userGetService;

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
                .findPagedByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList();
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

    public List<PostDto> getFollowersPosts(Long userId) {
        List<GetUserDto> followedUsers = userGetService.getFollowedUsers(userId);
        return followedUsers
                .stream()
                .map(user -> postRepository.findAllByUserId(user.getId()))
                .flatMap(List::stream)
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList();
    }

    public List<PostDto> getPagedFollowersPosts(Long userId, Integer pageNumber, Integer pageSize) {
        List<GetUserDto> followedUsers = userGetService.getFollowedUsers(userId);
        List<PostDto> followedUsersPosts = followedUsers
                .stream()
                .map(user -> postRepository.findAllByUserId(user.getId()))
                .flatMap(List::stream)
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(postToPostDtoMapper::map)
                .toList();
        return pageList(followedUsersPosts, pageNumber, pageSize);
    }

    private List<PostDto> pageList(List<PostDto> posts, Integer pageNumber, Integer pageSize) {
        if(pageNumber < 0 || pageSize < 0){
            throw new IllegalArgumentException("RequestParam pageNumber and pageSize must be >= 0");
        }
        if(pageSize == 0 || pageSize * pageNumber >= posts.size()){
            return List.of();
        }
        if(pageSize * pageNumber + pageSize >= posts.size()){
            return posts.subList(pageSize * pageNumber, posts.size());
        }
        return posts.subList(pageSize * pageNumber, pageSize * pageNumber + pageSize);
    }
}
