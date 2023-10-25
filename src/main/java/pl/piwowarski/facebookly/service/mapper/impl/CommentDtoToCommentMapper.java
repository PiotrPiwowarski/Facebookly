package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.reverseMap.ReverseMapper;

@Service
@RequiredArgsConstructor
public class CommentDtoToCommentMapper implements ReverseMapper<CommentDto, Comment> {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment map(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreated(commentDto.getCreated());
        comment.setUser(userRepository
                .findById(commentDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE))
        );
        comment.setPost(postRepository
                .findById(commentDto.getPostId())
                .orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE))
        );
        return comment;
    }
}
