package pl.piwowarski.facebookly.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchId;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;

@Service
@AllArgsConstructor
public class CommentMapper implements Mapper<Comment, CommentDto>{

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment map(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreated(commentDto.getCreated());
        comment.setUser(userRepository
                .findById(commentDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchId(NoUserWithSuchId.MESSAGE))
        );
        comment.setPost(postRepository
                .findById(commentDto.getPostId())
                .orElseThrow(() -> new NoPostWithSuchId(NoPostWithSuchId.MESSAGE))
        );
        return comment;
    }

    @Override
    public CommentDto unmap(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }
}
