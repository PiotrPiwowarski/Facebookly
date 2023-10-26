package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.AccessDeniedException;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.service.comment.CommentService;

import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class CommentRemovalService implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void deleteCommentById(Long commentId, Long userId, Role role) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(NoCommentWithSuchIdException::new);
        if(!comment.getUser().getId().equals(userId) && role != ADMIN){
            throw new AccessDeniedException();
        }
        commentRepository.deleteById(commentId);
    }

    private void deleteCommentById(Long commentId){
        commentRepository.deleteById(commentId);
    }

    public void deleteCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        comments.forEach(comment -> deleteCommentById(comment.getId()));
    }

    public void deleteAllPostComments(Long postId, Long userId, Role role) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(NoPostWithSuchIdException::new);
        if(!post.getUser().getId().equals(userId) && role == USER){
            throw new AccessDeniedException();
        }
        List<Comment> comments = post.getComments();
        commentRepository.deleteAll(comments);
    }
}
