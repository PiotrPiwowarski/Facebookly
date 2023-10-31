package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.Mapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentDtoToCommentMapper implements Mapper<AddCommentDto, Comment> {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment map(AddCommentDto addCommentDto) {
        return Comment
                .builder()
                .content(addCommentDto.getContent())
                .created(LocalDateTime.now())
                .user(userRepository
                        .findById(addCommentDto.getUserId())
                        .orElseThrow(NoUserWithSuchIdException::new))
                .post(postRepository
                        .findById(addCommentDto.getPostId())
                        .orElseThrow(NoPostWithSuchIdException::new))
                .build();
    }
}
