package pl.piwowarski.facebookly.service.comment;

import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.enums.Reaction;

import java.util.List;

public interface CommentService {

    CommentDto addComment(AddCommentDto addCommentDto);
    CommentDto getComment(long commentId);
    List<CommentDto> getAllPostComments(long postId);
    List<CommentDto> getPagePostComments(long postId, int pageNumber, int pageSize);
    void deleteComment(long commentId, long userId);
    void deletePostComments(long postId, long userId);
    CommentDto updateComment(long commentId, UpdateCommentDto updateCommentDto);
    void addCommentReaction(long commentId, long userId, Reaction reaction);
    List<UserReactionDto> getAllCommentReactions(long commentId, Reaction reaction);
    List<UserReactionDto> getAllCommentReactions(long commentId);
    void deleteCommentReaction(long commentId, long userId);
}
