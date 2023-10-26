package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
@RequiredArgsConstructor
public class CommentDtoToCommentMapper implements Mapper<CommentDto, Comment> {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment map(CommentDto commentDto) {
        return Comment
                .builder()
                .content(commentDto.getContent())
                .created(commentDto.getCreated())
                .user(userRepository
                        .findById(commentDto.getUserId())
                        .orElseThrow(NoUserWithSuchIdException::new))
                .post(postRepository
                        .findById(commentDto.getPostId())
                        .orElseThrow(NoPostWithSuchIdException::new))
                .build();
    }
}
