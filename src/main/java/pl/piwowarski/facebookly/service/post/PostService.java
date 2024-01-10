package pl.piwowarski.facebookly.service.post;

import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDataDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.dto.post.UpdatePostDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.enums.Reaction;

import java.util.List;

public interface PostService {

    PostDto addPost(AddPostDto addPostDto);
    PostDto getPostDto(long postId);
    Post getPost(long postId);
    List<PostDto> getAllPosts();
    List<PostDataDto> getAllPostsWithData();
    List<PostDto> getPagedPosts(int pageNumber, Integer pageSize);
    List<PostDto> getAllUserPosts(long userId);
    List<PostDto> getPagedUserPosts(long userId, int pageNumber, int pageSize);
    List<PostDto> getFollowedUsersPosts(long userId);
    List<PostDto> getPagedFollowedUsersPosts(long userId, int pageNumber, int pageSize);
    void deleteAllUserPosts(long userId);
    void deletePost(long postId, long userId);
    PostDto updatePost(long postId, UpdatePostDto updatePostDto);
    void addPostReaction(long postId, long userId, Reaction reaction);
    List<UserReactionDto> getAllPostReactions(long postId, Reaction reaction);
    int getReactionCount(long postId, Reaction reaction);
    List<UserReactionDto> getAllPostReactions(long postId);
    void deletePostReaction(long postId, long userId);
}
