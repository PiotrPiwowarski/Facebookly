package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class CommentToCommentDtoMapper implements Mapper<Comment, CommentDto> {

    @Override
    public CommentDto map(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getId())
                .build();
    }
}
