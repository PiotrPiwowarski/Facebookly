package pl.piwowarski.facebookly.service.comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.exception.AuthorizationException;
import pl.piwowarski.facebookly.exception.CommentContentIsNullException;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.mapper.impl.CommentToCommentDtoMapper;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentService {

    private final CommentToCommentDtoMapper commentToCommentDtoMapper;
    private final CommentGetService commentGetService;

    @Transactional
    public CommentDto updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = commentGetService.getCommentById(updateCommentDto.getId());
        if(!updateCommentDto.getUserId().equals(comment.getUser().getId())){
            throw new AuthorizationException();
        }
        if(updateCommentDto.getContent() == null){
            throw new CommentContentIsNullException();
        }
        comment.setContent(updateCommentDto.getContent());
        return commentToCommentDtoMapper.map(comment);
    }
}
