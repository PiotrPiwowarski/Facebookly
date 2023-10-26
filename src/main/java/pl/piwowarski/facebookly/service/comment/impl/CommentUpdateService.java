package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.CommentContentIsNullException;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.mapper.impl.CommentToCommentDtoMapper;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentService {

    private final CommentToCommentDtoMapper commentToCommentDtoMapper;
    private final CommentGetService commentGetService;

    @Transactional
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentGetService.getCommentById(commentDto.getId());
        if(commentDto.getContent() == null){
            throw new CommentContentIsNullException();
        }
        comment.setContent(commentDto.getContent());
        return commentToCommentDtoMapper.map(comment);
    }
}
