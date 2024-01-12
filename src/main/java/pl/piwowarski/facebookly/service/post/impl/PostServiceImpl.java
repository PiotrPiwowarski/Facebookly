package pl.piwowarski.facebookly.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.manager.ImageManager;
import pl.piwowarski.facebookly.mapper.PostMapper;
import pl.piwowarski.facebookly.mapper.UserReactionMapper;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDataDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.dto.post.UpdatePostDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.PostReaction;
import pl.piwowarski.facebookly.model.enums.Reaction;
import pl.piwowarski.facebookly.repository.PostReactionRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.post.PostService;
import pl.piwowarski.facebookly.service.user.UserService;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostReactionRepository postReactionRepository;
    private final ImageManager imageManager;
    private final UserService userService;

    @Override
    public PostDto addPost(AddPostDto addPostDto) {
        Post post = postRepository.save(PostMapper.toEntity(addPostDto, imageManager, userService));
        return PostMapper.toDto(post);
    }

    @Override
    public PostDto getPostDto(long postId) {
        Post post = getPost(postId);
        return PostMapper.toDto(post);
    }


    @Override
    public Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NoPostWithSuchIdException::new);
    }
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::toDto)
                .sorted(Comparator
                        .comparing(PostDto::getCreated)
                        .reversed())
                .toList();
    }

    @Override
    public List<PostDataDto> getAllPostsWithData() {
        List<Object[]> results = postRepository.findAllPostsData();
        List<PostDataDto> postsWithData = new LinkedList<>();
        for(var r: results) {
            PostDataDto postDataDto = PostDataDto.builder()
                    .postId((long) r[0])
                    .content(r[1] != null ? (String) r[1] :null)
                    .image(r[2] != null ? castToBytesArray((Blob) r[2]) : null)
                    .created(((Timestamp)r[3]).toLocalDateTime())
                    .userId((long) r[4])
                    .firstName((String) r[5])
                    .lastName((String) r[6])
                    .likes((long) r[7])
                    .dislikes((long) r[8])
                    .build();
            postsWithData.add(postDataDto);
        }
        return postsWithData.stream()
                .sorted(Comparator
                        .comparing(PostDataDto::getCreated)
                        .reversed())
                .toList();
    }

    private byte[] castToBytesArray(Blob blob) {
        try {
            return blob.getBytes(0, (int)blob.length());
        } catch(SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<PostDto> getPagedPosts(int pageNumber, Integer pageSize) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return posts.stream()
                .map(PostMapper::toDto)
                .sorted(Comparator
                        .comparing(PostDto::getCreated)
                        .reversed())
                .toList();
    }

    @Override
    public List<PostDto> getAllUserPosts(long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getPagedUserPosts(long userId, int pageNumber, int pageSize) {
        List<Post> posts = postRepository.findPagedByUserId(userId, PageRequest.of(pageNumber, pageSize));
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getFollowedUsersPosts(long userId) {
        List<UserDto> followedUsers = userService.getFollowedUsers(userId);
        return followedUsers
                .stream()
                .map(user -> postRepository.findAllByUserId(user.getId()))
                .flatMap(List::stream)
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getPagedFollowedUsersPosts(long userId, int pageNumber, int pageSize) {
        return postRepository.findPagedFollowedUsersPosts(userId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .sorted(Comparator
                        .comparing(Post::getCreated)
                        .reversed())
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public void deleteAllUserPosts(long userId) {
        postRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deletePost(long postId, long userId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(NoPostWithSuchIdException::new);
        if(!post.getUser().getId().equals(userId)){
            throw new AccessDeniedException();
        }
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto updatePost(long postId, UpdatePostDto updatePostDto) {
        Optional<Post> optionalPost = postRepository.findByIdAndUserId(postId, updatePostDto.getUserId());
        if(optionalPost.isEmpty()) {
            throw new AccessDeniedException();
        }
        Post post = optionalPost.get();
        if(updatePostDto.getContent() != null) {
            post.setContent(updatePostDto.getContent());
        }
        if(updatePostDto.getImagePath() != null) {
            post.setImage(imageManager.fromPathToBytesArray(updatePostDto.getImagePath()));
        }
        return PostMapper.toDto(postRepository.save(post));
    }

    @Override
    public void addPostReaction(long postId, long userId, Reaction reaction) {
        Optional<PostReaction> postReactionOptional = postReactionRepository.findByPostIdAndUserId(postId, userId);
        if(postReactionOptional.isPresent()) {
            if(postReactionOptional.get().getReaction().equals(reaction)) {
                postReactionOptional.ifPresent(postReactionRepository::delete);
                return;
            } else {
                postReactionOptional.ifPresent(postReactionRepository::delete);
            }
        }
        PostDto postDto = getPostDto(postId);
        PostReaction postReaction = PostReaction.builder()
                .user(userService.getUser(userId))
                .post(PostMapper.toEntity(postDto, userService))
                .reaction(reaction)
                .build();
        postReactionRepository.save(postReaction);
    }

    @Override
    public List<UserReactionDto> getAllPostReactions(long postId, Reaction reaction) {
        return postReactionRepository
                .findByPostId(postId)
                .stream()
                .filter(postReaction -> postReaction.getReaction().equals(reaction))
                .map(postReaction -> UserReactionMapper
                        .toDto(postReaction.getUser(), postReaction.getReaction()))
                .toList();
    }

    @Override
    public int getReactionCount(long postId, Reaction reaction) {
		return getAllPostReactions(postId, reaction).size();
    }

    @Override
    public List<UserReactionDto> getAllPostReactions(long postId) {
        return postReactionRepository
                .findByPostId(postId)
                .stream()
                .map(postReaction -> UserReactionMapper
                        .toDto(postReaction.getUser(), postReaction.getReaction()))
                .toList();
    }

    @Override
    public void deletePostReaction(long postId, long userId) {
        Optional<PostReaction> optionalPostReaction = postReactionRepository.findByPostIdAndUserId(postId, userId);
        optionalPostReaction.ifPresent(postReactionRepository::delete);
    }
}
