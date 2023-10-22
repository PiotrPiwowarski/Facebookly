package pl.piwowarski.facebookly.service.mapper.map.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;

@Service
public class CommentMapper implements Mapper<Comment, CommentDto> {
    @Override
    public CommentDto map(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreated(comment.getCreated());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setLikes(comment.getLikes());
        commentDto.setDislikes(comment.getDislikes());
        return commentDto;
    }
}
