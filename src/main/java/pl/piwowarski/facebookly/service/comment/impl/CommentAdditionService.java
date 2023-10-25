package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.mapper.impl.CommentDtoToCommentMapper;
import pl.piwowarski.facebookly.service.mapper.impl.CommentToCommentDtoMapper;

@Service
@RequiredArgsConstructor
public class CommentAdditionService implements CommentService {

    private final CommentDtoToCommentMapper commentDtoToCommentMapper;
    private final CommentToCommentDtoMapper commentToCommentDtoMapper;
    private final CommentRepository commentRepository;

    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = commentDtoToCommentMapper.map(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return commentToCommentDtoMapper.map(savedComment);
    }
}
