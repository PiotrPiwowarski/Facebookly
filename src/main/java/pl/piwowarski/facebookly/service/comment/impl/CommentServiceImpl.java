package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchIdException;
import pl.piwowarski.facebookly.mapper.CommentMapper;
import pl.piwowarski.facebookly.mapper.UserReactionMapper;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.model.entity.CommentReaction;
import pl.piwowarski.facebookly.model.enums.Reaction;
import pl.piwowarski.facebookly.repository.CommentReactionRepository;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.post.PostService;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public CommentDto addComment(AddCommentDto addCommentDto) {
        Comment comment = CommentMapper.toEntity(addCommentDto, userService, postService);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toDto(savedComment);
    }

    @Override
    public CommentDto getComment(long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(NoCommentWithSuchIdException::new);
        return CommentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllPostComments(long postId) {
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(CommentMapper::toDto)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated))
                .toList();
    }

    @Override
    public List<CommentDto> getPagePostComments(long postId, int pageNumber, int pageSize) {
        return commentRepository
                .findPagedByPostId(postId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(CommentMapper::toDto)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated))
                .toList();
    }

    @Override
    public void deleteComment(long commentId, long userId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(NoCommentWithSuchIdException::new);
        if(!comment.getUser().getId().equals(userId)){
            throw new AccessDeniedException();
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deletePostComments(long postId, long userId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        if(comments.size() > 0 && !comments.get(0).getPost().getUser().getId().equals(userId)){
            throw new AccessDeniedException();
        }
        commentRepository.deleteAll(comments);
    }

    @Override
    public CommentDto updateComment(long commentId, UpdateCommentDto updateCommentDto) {
        Optional<Comment> optionalComment = commentRepository.findByIdAndUserId(commentId, updateCommentDto.getUserId());
        if(optionalComment.isEmpty()) {
            throw new AccessDeniedException();
        }
        Comment comment = optionalComment.get();
        comment.setContent(updateCommentDto.getContent());
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void addCommentReaction(long commentId, long userId, Reaction reaction) {
        Optional<CommentReaction> commentReactionOptional = commentReactionRepository
                .findByCommentIdAndUserId(commentId, userId);
        commentReactionOptional.ifPresent(commentReactionRepository::delete);
        CommentReaction commentReaction = CommentReaction.builder()
                .user(userService.getUser(userId))
                .comment(CommentMapper.toEntity(getComment(commentId), userService, postService))
                .reaction(reaction)
                .build();
        commentReactionRepository.save(commentReaction);
    }

    @Override
    public List<UserReactionDto> getAllCommentReactions(long commentId, Reaction reaction) {
        return commentReactionRepository
                .findByCommentId(commentId)
                .stream()
                .filter(commentReaction -> commentReaction.getReaction().equals(reaction))
                .map(commentReaction -> UserReactionMapper
                        .toDto(commentReaction.getUser(), commentReaction.getReaction()))
                .toList();
    }

    @Override
    public int getReactionCount(long commentId, Reaction reaction) {
        return getAllCommentReactions(commentId, reaction).size();
    }

    @Override
    public List<UserReactionDto> getAllCommentReactions(long commentId) {
        return commentReactionRepository
                .findByCommentId(commentId)
                .stream()
                .map(commentReaction -> UserReactionMapper
                        .toDto(commentReaction.getUser(), commentReaction.getReaction()))
                .toList();
    }

    @Override
    public void deleteCommentReaction(long commentId, long userId) {
        Optional<CommentReaction> commentReactionDto = commentReactionRepository
                .findByCommentIdAndUserId(commentId, userId);
        commentReactionDto.ifPresent(commentReactionRepository::delete);
    }
}
