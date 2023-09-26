package pl.piwowarski.facebookly.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.CommentContentIsNull;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchId;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.service.mapper.CommentMapper;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAllCommentsByPostId(Long postId){
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(commentMapper::unmap)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }

    public List<CommentDto> findAllCommentsByPostId(Long postId, Integer pageNumber, Integer pageSize){
        return commentRepository
                .findAllByPostId(postId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(commentMapper::unmap)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = commentMapper.map(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.unmap(savedComment);
    }

    public void deleteById(Long commentId) {
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
                        () -> new NoCommentWithSuchId(NoCommentWithSuchId.MESSAGE)
                );
    }

    @Transactional
    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Comment comment = findCommentById(commentId);
        if(commentDto.getContent() == null){
            throw new CommentContentIsNull(CommentContentIsNull.MESSAGE);
        }
        comment.setContent(commentDto.getContent());
        return commentMapper.unmap(comment);
    }

    public void deleteByUserId(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        comments.forEach(comment -> deleteById(comment.getId()));
    }

    public void deleteAllPostComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        commentRepository.deleteAll(comments);
    }
}
