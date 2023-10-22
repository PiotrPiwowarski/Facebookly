package pl.piwowarski.facebookly.service.entityService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.CommentContentIsNullException;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.mapper.map.impl.CommentMapper;
import pl.piwowarski.facebookly.service.mapper.reverseMap.impl.CommentReverseMapper;

import java.util.Comparator;
import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentReverseMapper commentReverseMapper;

    public List<CommentDto> findAllCommentsByPostId(Long postId){
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(commentMapper::map)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }

    public List<CommentDto> findAllCommentsByPostId(Long postId, Integer pageNumber, Integer pageSize){
        return commentRepository
                .findAllByPostId(postId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(commentMapper::map)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = commentReverseMapper.map(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.map(savedComment);
    }

    public void deleteById(Long commentId, Long userId, Role role) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NoCommentWithSuchIdException(NoCommentWithSuchIdException.MESSAGE));
        if(!comment.getUser().getId().equals(userId) && role != ADMIN){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        commentRepository.deleteById(commentId);
    }

    public void deleteById(Long commentId){
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void addLike(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setLikes(comment.getLikes() + 1);
    }

    @Transactional
    public void addDislike(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setDislikes(comment.getDislikes() + 1);
    }

    private Comment findCommentById(Long commentId){
        return commentRepository
                .findById(commentId)
                .orElseThrow(
                        () -> new NoCommentWithSuchIdException(NoCommentWithSuchIdException.MESSAGE)
                );
    }

    @Transactional
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = findCommentById(commentDto.getId());
        if(commentDto.getContent() == null){
            throw new CommentContentIsNullException(CommentContentIsNullException.MESSAGE);
        }
        comment.setContent(commentDto.getContent());
        return commentMapper.map(comment);
    }

    public void deleteByUserId(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        comments.forEach(comment -> deleteById(comment.getId()));
    }

    public void deleteAllPostComments(Long postId, Long userId, Role role) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE));
        if(!post.getUser().getId().equals(userId) && role == USER){
            throw new AccessDeniedException(AccessDeniedException.MESSAGE);
        }
        List<Comment> comments = post.getComments();
        commentRepository.deleteAll(comments);
    }
}
