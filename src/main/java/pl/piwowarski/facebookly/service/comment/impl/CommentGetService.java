package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.mapper.impl.CommentToCommentDtoMapper;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentGetService implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentToCommentDtoMapper commentToCommentDtoMapper;

    public Comment getCommentById(Long commentId){
        return commentRepository
                .findById(commentId)
                .orElseThrow(NoCommentWithSuchIdException::new);
    }

    public List<CommentDto> getAllCommentsByPostId(Long postId){
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(commentToCommentDtoMapper::map)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }
    public List<CommentDto> getAllPagedCommentsByPostId(Long postId, Integer pageNumber, Integer pageSize){
        return commentRepository
                .findAllByPostId(postId, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(commentToCommentDtoMapper::map)
                .sorted(Comparator
                        .comparing(CommentDto::getCreated)
                        .reversed())
                .toList();
    }
}
