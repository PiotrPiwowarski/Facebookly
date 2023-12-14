package pl.piwowarski.facebookly.mapper;

import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.service.post.PostService;
import pl.piwowarski.facebookly.service.user.UserService;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CommentMapper {

    public static Comment toEntity(AddCommentDto addCommentDto,
                                   UserService userService,
                                   PostService postService) {
        return Comment.builder()
                .content(addCommentDto.getContent())
                .user(userService.getUser(addCommentDto.getUserId()))
                .post(postService.getPost(addCommentDto.getPostId()))
                .created(LocalDateTime.now())
                .build();
    }

    public static Comment toEntity(CommentDto commentDto,
                                   UserService userService,
                                   PostService postService) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .created(commentDto.getCreated())
                .user(userService.getUser(commentDto.getUserId()))
                .post(postService.getPost(commentDto.getPostId()))
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getId())
                .build();
    }
}
